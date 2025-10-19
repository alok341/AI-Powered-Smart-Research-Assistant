# 🧠 Research Assistant Chrome Extension  
**An AI-powered Chrome extension that helps you research, analyze, and organize information from any webpage.**

---

## 🚀 Features

### 🔍 Core Research Functions
- **Summarize** — Instantly generate concise summaries of selected text  
- **Explain** — Understand complex concepts in simple terms  
- **Key Points** — Extract main ideas as actionable bullet points  
- **Suggest** — Discover related topics and further reading  

### ⚙️ Productivity Tools
- **Research Notes** — Save and organize your findings  
- **Export Notes** — Download your research as `.txt` files  
- **Context Menu** — Right-click selected text for quick AI actions  
- **Auto-save** — All notes are stored locally and automatically  

---

## 🛠️ Installation

### 📋 Prerequisites
- **Chrome Browser** (version 88 or higher)  
- **Java 17+**  
- **Spring Boot 3.x**  
- **Gemini API Key** from [Google AI Studio](https://makersuite.google.com/app/apikey)

---

### ⚙️ Backend Setup

```bash
# Clone the repository
git clone <repository-url>
cd research-assistant
Configure API Keys
Create a file named application.properties inside src/main/resources:

properties
Copy code
gemini.api.url=https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=
gemini.api.key=YOUR_GEMINI_API_KEY
server.port=8080
Run the Backend
bash
Copy code
./mvnw spring-boot:run
Your backend will start on http://localhost:8080

🧩 Chrome Extension Setup
Open Chrome and go to chrome://extensions/

Enable Developer Mode (toggle in the top-right corner)

Click Load Unpacked

Select the extension/ folder containing:

manifest.json

sidepanel.html

sidepanel.css

sidepanel.js

background.js

✅ Once loaded, you should see the Research Assistant icon in your Chrome toolbar.

📖 How to Use
🪄 Basic Usage
Select Text — Highlight any text on a webpage

Choose Action — Click one of the buttons in the side panel:

📝 Summarize – Get a brief overview

🔍 Explain – Understand clearly

✅ Key Points – Extract main ideas

💡 Suggest – Discover related insights

⚡ Advanced Features
Right-click Menu — Instantly access AI tools via the context menu

Research Notes

Write your own notes in the text area

Notes auto-save locally

Export them anytime as .txt files

Instant Results — AI-generated outputs appear at the top of the panel

⌨️ Keyboard Shortcuts
Right-click selected text → choose AI action

Toolbar icon → open or close the Research Assistant panel

🏗️ Architecture Overview
🖥️ Frontend (Chrome Extension)
Side Panel UI — Clean, responsive interface

Content Scripts — Handle text selections

Background Service — Manage context menus & side panel

Chrome Storage — Persist notes locally

⚙️ Backend (Spring Boot)
REST API — /api/research/process

Gemini AI Integration — Uses Google’s generative model

Request Processing — Builds context-aware prompts dynamically

Response Parsing — Extracts structured summaries

🔧 Configuration
🧠 Backend Endpoints
Method	Endpoint	Description
POST	/api/research/process	Main AI research processing
GET	/api/research/health	Check backend service status

🔐 Extension Permissions
activeTab — Access webpage content

storage — Save research notes

sidePanel — Display research UI

contextMenus — Enable right-click integration

scripting — Extract selected text

🐛 Troubleshooting
Common Issues
Issue	Cause	Solution
API Error	Backend not running or invalid key	Ensure backend is running and API key is valid
No text selected	Text area empty or protected	Highlight at least 10 characters of visible text
Extension not loading	Missing or misplaced files	Recheck folder structure and reload extension
Notes not saving	Chrome storage issue	Allow storage permission and ensure notes contain text

Debug Mode
Right-click the extension icon → Inspect popup

Open Console to check error messages

View Network Tab for API request logs

📁 File Structure
swift
Copy code
research-assistant/
├── backend/
│   ├── src/main/java/com/alok/assistant/
│   │   ├── controller/
│   │   │   └── ResearchController.java
│   │   ├── service/
│   │   │   └── ResearchService.java
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
🗂️ Local Storage Only — Notes stay in your browser

❌ No Data Collection — No user data sent externally

🔑 Secure API Keys — Use your personal Gemini key

⚙️ Minimal Permissions — Only what's necessary

🌱 Future Enhancements
🔄 Multi-AI Provider Support

🧩 Custom Prompt Templates

🧠 Research Session Management

📚 Citation Generation

🌐 Cross-Browser Support (Edge, Firefox)

📄 License
This project is licensed under the MIT License — free to use, modify, and distribute.

🤝 Contributing
Fork this repository

Create your feature branch (git checkout -b feature-name)

Commit your changes (git commit -m "Add feature")

Push to the branch (git push origin feature-name)

Create a Pull Request 🎉

📞 Support
For questions or issues:

Review the Troubleshooting section

Check your Chrome Developer Tools Console

Ensure the backend service is running

Happy Researching! 🎯
Simplify your research. One click at a time.
