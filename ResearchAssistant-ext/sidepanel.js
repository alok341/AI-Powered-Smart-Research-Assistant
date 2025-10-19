document.addEventListener('DOMContentLoaded', () => {
    initializeApp();
});

async function initializeApp() {
    // Load saved notes
    chrome.storage.local.get(['researchNotes'], (result) => {
        if (result.researchNotes) {
            document.getElementById('notes').value = result.researchNotes;
        }
        updateNotesButtons(); // Enable/disable based on initial content
    });

    // Event listeners
    document.getElementById('summarizeBtn').addEventListener('click', () => processText('summarize'));
    document.getElementById('suggestBtn').addEventListener('click', () => processText('suggest'));
    document.getElementById('explainBtn').addEventListener('click', () => processText('explain'));
    document.getElementById('keypointsBtn').addEventListener('click', () => processText('keypoints'));
    
    document.getElementById('saveNotesBtn').addEventListener('click', saveNotes);
    document.getElementById('clearNotesBtn').addEventListener('click', clearNotes);
    document.getElementById('exportNotesBtn').addEventListener('click', exportNotes);

    // Listen for notes input changes
    document.getElementById('notes').addEventListener('input', updateNotesButtons);

    updateStatus('Ready', 'success');
}

function updateNotesButtons() {
    const notes = document.getElementById('notes').value.trim();
    const hasNotes = notes.length > 0;
    
    document.getElementById('saveNotesBtn').disabled = !hasNotes;
    document.getElementById('clearNotesBtn').disabled = !hasNotes;
    document.getElementById('exportNotesBtn').disabled = !hasNotes;
}

async function processText(operation) {
    try {
        updateStatus('Processing...', 'loading');
        disableButtons(true);

        const [tab] = await chrome.tabs.query({ active: true, currentWindow: true });
        const [{ result }] = await chrome.scripting.executeScript({
            target: { tabId: tab.id },
            function: () => window.getSelection().toString()
        });

        if (!result || result.trim().length < 10) {
            showResult('Please select meaningful text (at least 10 characters)');
            updateStatus('Selection too short', 'error');
            disableButtons(false);
            return;
        }

        const request = {
            content: result,
            operation: operation
        };

        const response = await fetch('http://localhost:8080/api/research/process', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(request)
        });

        if (!response.ok) {
            throw new Error(`API Error: ${response.status}`);
        }

        const text = await response.text();
        showResult(text, operation);
        updateStatus('Completed', 'success');

    } catch (error) {
        showResult(`Error: ${error.message}`);
        updateStatus('Failed', 'error');
        console.error('Processing error:', error);
    } finally {
        disableButtons(false);
    }
}

function showResult(content, operation = '') {
    const timestamp = new Date().toLocaleTimeString();
    const resultsDiv = document.getElementById('results');
    
    const resultHTML = `
        <div class="result-item">
            <div class="result-content">${content.replace(/\n/g, '<br>')}</div>
            <div class="result-meta">
                ${operation ? `${operation} | ` : ''}${timestamp}
            </div>
        </div>
    `;
    
    resultsDiv.innerHTML = resultHTML;
}

function updateStatus(message, type = '') {
    const statusElement = document.getElementById('status');
    statusElement.textContent = message;
    statusElement.className = type ? `status-${type}` : '';
}

function disableButtons(disabled) {
    const buttons = document.querySelectorAll('.btn.primary');
    buttons.forEach(btn => {
        btn.disabled = disabled;
    });
}

async function saveNotes() {
    const notes = document.getElementById('notes').value;
    if (!notes.trim()) return; // Double check
    
    await chrome.storage.local.set({ 'researchNotes': notes });
    updateStatus('Notes saved', 'success');
    
    // Show temporary confirmation
    const btn = document.getElementById('saveNotesBtn');
    const originalText = btn.textContent;
    btn.textContent = '✓ Saved';
    setTimeout(() => {
        btn.textContent = originalText;
    }, 2000);
}

async function clearNotes() {
    const notes = document.getElementById('notes').value;
    if (!notes.trim()) return; // Double check
    
    if (confirm('Are you sure you want to clear all notes?')) {
        document.getElementById('notes').value = '';
        await chrome.storage.local.remove('researchNotes');
        updateStatus('Notes cleared', 'success');
        updateNotesButtons(); // Disable buttons after clearing
    }
}

async function exportNotes() {
    const notes = document.getElementById('notes').value;
    if (!notes.trim()) return; // Double check

    const blob = new Blob([notes], { type: 'text/plain' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `research-notes-${new Date().toISOString().split('T')[0]}.txt`;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    URL.revokeObjectURL(url);
    
    updateStatus('Notes exported', 'success');
}