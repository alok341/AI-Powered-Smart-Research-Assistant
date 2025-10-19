package com.alok.Assistant.service;

import com.alok.Assistant.model.GeminiResponse;
import com.alok.Assistant.model.ResearchRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ResearchService {
    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String getGeminiApiKey;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    // In-memory storage for session data
    private final Map<String, String> researchHistory = new ConcurrentHashMap<>();
    private final Map<String, Integer> usageStats = new ConcurrentHashMap<>();

    public ResearchService(WebClient.Builder webClient, ObjectMapper objectMapper) {
        this.webClient = webClient.build();
        this.objectMapper = objectMapper;
    }

    public String processContent(ResearchRequest request) {
        // Track usage
        usageStats.merge(request.getOperation(), 1, Integer::sum);

        //buildPrompt
        String prompt = buildPrompt(request);

        //Store the research query
        String sessionKey = System.currentTimeMillis() + "_" + request.getOperation();
        researchHistory.put(sessionKey, request.getContent().substring(0, Math.min(100, request.getContent().length())));

        //Query the AI Model Api
        Map<String,Object> requestBody = Map.of(
                "contents",new Object[] {
                        Map.of("parts",new Object[]{
                                Map.of("text",prompt)
                        })
                },
                "generationConfig", Map.of(
                        "temperature", getTemperatureForOperation(request.getOperation()),
                        "topK", 40,
                        "topP", 0.95,
                        "maxOutputTokens", getMaxTokensForOperation(request.getOperation())
                )
        );

        try {
            String response = webClient.post()
                    .uri(geminiApiUrl+getGeminiApiKey)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            //Parse the response and Return Response
            return extractTextFromResponse(response);
        } catch (Exception e) {
            return "Error processing request: " + e.getMessage();
        }
    }

    private String buildPrompt(ResearchRequest request){
        StringBuilder prompt = new StringBuilder();
        switch (request.getOperation()) {
            case "summarize":
                prompt.append("Provide a clear and concise summary of the following text in" +
                        " few sentences in easy language and easy to understand. " +
                        "Highlight key points and main ideas:\n\n");
                break;
            case "suggest":
                prompt.append("Based on the following content, suggest related topics and further reading. " +
                        "Format the response with clear headings and bullet points. " +
                        "Also provide brief explanations for each suggestion:\n\n");
                break;
            case "explain":
                prompt.append("Explain the following content in simple terms as if explaining to a beginner. " +
                        "Break down complex concepts and provide examples where helpful:\n\n");
                break;
            case "keypoints":
                prompt.append("Extract the key points from the following text. " +
                        "Present them as a numbered list with clear, actionable items:\n\n");
                break;
            default:
                throw new IllegalArgumentException("Unknown Operation: " + request.getOperation());
        }
        prompt.append(request.getContent());
        return prompt.toString();
    }

    private double getTemperatureForOperation(String operation) {
        switch (operation) {
            case "summarize":
            case "keypoints":
                return 0.2; // More deterministic
            case "suggest":
            case "explain":
                return 0.7; // More creative
            default:
                return 0.4;
        }
    }

    private int getMaxTokensForOperation(String operation) {
        switch (operation) {
            case "summarize":
            case "keypoints":
                return 500;
            case "suggest":
            case "explain":
                return 800;
            case "translate":
                return 1000;
            default:
                return 600;
        }
    }

    private String extractTextFromResponse(String response){
        try {
            GeminiResponse geminiResponse = objectMapper.readValue(response, GeminiResponse.class);
            if (geminiResponse.getCandidates() != null && !geminiResponse.getCandidates().isEmpty()) {
                GeminiResponse.Candidate firstCandidate = geminiResponse.getCandidates().get(0);
                if (firstCandidate.getContent() != null && firstCandidate.getContent().getParts() != null
                        && !firstCandidate.getContent().getParts().isEmpty()) {
                    return firstCandidate.getContent().getParts().get(0).getText();
                }
            }
        } catch (Exception e){
            return "Error Parsing: " + e.getMessage();
        }
        return "No content found";
    }

    // New methods for enhanced features
    public Map<String, String> getResearchHistory() {
        return new ConcurrentHashMap<>(researchHistory);
    }

    public Map<String, Integer> getUsageStats() {
        return new ConcurrentHashMap<>(usageStats);
    }

    public void clearHistory() {
        researchHistory.clear();
    }
}
