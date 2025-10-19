package com.alok.Assistant.controller;

import com.alok.Assistant.model.ResearchRequest;
import com.alok.Assistant.service.ResearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/research")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ResearchController {
    private final ResearchService researchService;

    @PostMapping("/process")
    public ResponseEntity<String> processContent(@RequestBody ResearchRequest request){
        try {
            String result = researchService.processContent(request);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    // New endpoints for enhanced features
    @GetMapping("/history")
    public ResponseEntity<Map<String, String>> getResearchHistory(){
        return ResponseEntity.ok(researchService.getResearchHistory());
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Integer>> getUsageStats(){
        return ResponseEntity.ok(researchService.getUsageStats());
    }

    @PostMapping("/clear-history")
    public ResponseEntity<String> clearHistory(){
        researchService.clearHistory();
        return ResponseEntity.ok("Research history cleared");
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> healthCheck(){
        return ResponseEntity.ok(Map.of("status", "active", "service", "Research Assistant"));
    }
}
