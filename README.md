Research Assistant Chrome Extension
A powerful AI-powered Chrome extension that helps you research, analyze, and organize information from any webpage.

🚀 Features
Core Research Functions
Summarize: Get concise summaries of selected text

Explain: Understand complex concepts in simple terms

Key Points: Extract main ideas as actionable bullet points

Suggest: Discover related topics and further reading

Productivity Tools
Research Notes: Save and organize your findings

Export Notes: Download your research as text files

Context Menu: Right-click selected text for quick actions

Auto-save: Notes are automatically saved locally

🛠️ Installation
Prerequisites
Chrome Browser (version 88+)

Java 17+ (for backend)

Spring Boot 3.x

Gemini API key from Google AI Studio

Backend Setup
Clone the repository

bash
git clone <repository-url>
cd research-assistant
Configure API keys
Create application.properties:

properties
gemini.api.url=https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=
gemini.api.key=YOUR_GEMINI_API_KEY
server.port=8080
Run the backend

bash
./mvnw spring-boot:run
Extension Installation
Open Chrome Extensions

Navigate to chrome://extensions/

Enable "Developer mode" (toggle in top-right)

Load Extension

Click "Load unpacked"

Select the extension folder containing:

manifest.json

sidepanel.html

sidepanel.css

sidepanel.js

background.js

Verify Installation

Look for Research Assistant icon in toolbar

Click to open side panel

📖 How to Use
Basic Usage
Select Text: Highlight any text on a webpage

Choose Action: Click one of the research buttons:

📝 Summarize - Get brief overview

🔍 Explain - Simple explanations

✅ Key Points - Main ideas as bullets

💡 Suggest - Related topics

Advanced Features
Right-click Menu: Right-click selected text for quick access

Research Notes:

Type notes in the text area

Buttons enable automatically when text is entered

Save locally or export as file

Results: Appear at the top for immediate visibility

Keyboard Shortcuts
Right-click selected text for context menu options

Use toolbar icon for full feature access

🏗️ Architecture
Frontend (Chrome Extension)
Side Panel UI: Clean, responsive interface

Content Scripts: Text selection handling

Background Service: Context menu and panel management

Chrome Storage: Local data persistence

Backend (Spring Boot)
REST API: /api/research/process

Gemini AI Integration: Google's generative AI

Request Processing: Dynamic prompt generation

Response Parsing: Structured data extraction

🔧 Configuration
Backend Endpoints
POST /api/research/process - Main research processing

GET /api/research/health - Service status check

Extension Permissions
activeTab - Access current webpage content

storage - Save notes and preferences

sidePanel - Display research interface

contextMenus - Right-click integration

scripting - Text selection capabilities

🐛 Troubleshooting
Common Issues
"API Error"

Check backend is running on port 8080

Verify Gemini API key is valid

Ensure internet connection

"No text selected"

Select at least 10 characters of text

Ensure text is not in protected areas

Extension not loading

Verify all files are in correct location

Check Chrome version compatibility

Reload extension in chrome://extensions

Notes not saving

Check Chrome storage permissions

Ensure notes contain text before saving

Debug Mode
Open Chrome Developer Tools:

Right-click extension icon → "Inspect popup"

Check Console for error messages

Network tab for API calls

📁 File Structure
text
research-assistant/
├── backend/
│   ├── src/main/java/com/alok/Assistant/
│   │   ├── controller/ResearchController.java
│   │   ├── service/ResearchService.java
│   │   └── model/
│   │       ├── ResearchRequest.java
│   │       └── GeminiResponse.java
│   └── application.properties
└── extension/
    ├── manifest.json
    ├── sidepanel.html
    ├── sidepanel.css
    ├── sidepanel.js
    └── background.js
🔒 Privacy & Security
Local Storage: Notes saved only in your browser

No Data Collection: No user data sent to external servers

API Keys: Configure your own Gemini API key

Permissions: Minimal required permissions only

🚀 Future Enhancements
Multiple AI provider support

Custom prompt templates

Research session management

Citation generation

Cross-browser support

📄 License
MIT License - feel free to modify and distribute.

🤝 Contributing
Fork the repository

Create feature branch

Submit pull request with description

📞 Support
For issues and questions:

Check troubleshooting section

Review Chrome extension documentation

Verify backend service is running

Happy Researching! 🎯
