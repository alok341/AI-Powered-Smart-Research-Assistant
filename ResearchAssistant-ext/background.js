// Set side panel behavior
chrome.sidePanel.setPanelBehavior({ openPanelOnActionClick: true });

// Context menu for quick actions
chrome.runtime.onInstalled.addListener(() => {
    // Remove existing menus first
    chrome.contextMenus.removeAll(() => {
        // Create context menu items
        chrome.contextMenus.create({
            id: "summarize",
            title: "Summarize with Research Assistant",
            contexts: ["selection"]
        });

        chrome.contextMenus.create({
            id: "explain",
            title: "Explain with Research Assistant",
            contexts: ["selection"]
        });
    });
});

// Handle context menu clicks
chrome.contextMenus.onClicked.addListener((info, tab) => {
    if (info.selectionText && info.menuItemId) {
        // Store the selected text and operation
        chrome.storage.local.set({ 
            pendingSelection: {
                text: info.selectionText,
                operation: info.menuItemId
            }
        }, () => {
            // Open side panel
            chrome.sidePanel.open({ tabId: tab.id });
        });
    }
});