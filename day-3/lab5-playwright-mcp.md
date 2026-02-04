# Lab 5: Playwright MCP Server

## Overview
The Playwright MCP Server enables GitHub Copilot to automate web browser interactions, perform web scraping, test web applications, and capture screenshots through natural language commands.

## Prerequisites
- VS Code with GitHub Copilot extension installed
- Node.js (v18 or higher) and npm installed
- Internet connection for browser automation

## Installation

### Step 1: Install Playwright MCP Server

```bash
# Install globally
npm install -g @playwright/mcp

# Or install locally in your project
npm install --save-dev @playwright/mcp
```

### Step 2: Install Playwright Browsers

```bash
# Install all browsers (Chromium, Firefox, WebKit)
npx playwright install

# Or install specific browser
npx playwright install chromium
```

### Step 3: Configure MCP Server in VS Code

1. **Open mcp.json configuration**
   - **macOS:** `~/Library/Application Support/Code/User/mcp.json`
   - **Windows:** `%APPDATA%\Code\User\mcp.json`

2. **Add Playwright MCP Server configuration**

```json
{
  "servers": {
    "playwright": {
      "type": "stdio",
      "command": "npx",
      "args": [
        "@playwright/mcp@latest"
      ]
    }
  }
}
```

### Step 4: Restart VS Code

## Accessing with GitHub Copilot

### 1. Basic Navigation

```
Open Google in a browser
```

```
Navigate to https://github.com
```

### 2. Web Scraping

```
Go to https://news.ycombinator.com and get all the article titles
```

```
Extract all product prices from https://example-shop.com
```

### 3. Form Interaction

```
Go to https://example.com/login, fill in username "testuser" and password "testpass", then click submit
```

```
Search for "AI tools" on Google and show me the first 5 results
```

### 4. Screenshot Capture

```
Take a screenshot of https://github.com/microsoft/vscode
```

```
Capture a full-page screenshot of the VS Code documentation
```

### 5. Testing

```
Test if the login button is visible on https://example.com/login
```

```
Verify that clicking the "Sign Up" button redirects to /register
```

### 6. Data Extraction

```
Go to Wikipedia and extract the main content from the Python programming language page
```

```
Get all image URLs from https://example.com/gallery
```

### 7. Advanced Interactions

```
Wait for the page to fully load, then click on the first product in the list
```

```
Fill out the registration form with test data and submit it
```

## Advanced Configuration

### Headless Mode (Default)

```json
{
  "servers": {
    "playwright": {
      "type": "stdio",
      "command": "npx",
      "args": [
        "@playwright/mcp@latest"
      ],
      "env": {
        "PLAYWRIGHT_HEADLESS": "true"
      }
    }
  }
}
```

### Headed Mode (Show Browser)

```json
{
  "servers": {
    "playwright": {
      "type": "stdio",
      "command": "npx",
      "args": [
        "@playwright/mcp@latest"
      ],
      "env": {
        "PLAYWRIGHT_HEADLESS": "false"
      }
    }
  }
}
```

### Specific Browser

```json
{
  "servers": {
    "playwright-chrome": {
      "type": "stdio",
      "command": "npx",
      "args": [
        "@playwright/mcp@latest",
        "--browser", "chromium"
      ]
    },
    "playwright-firefox": {
      "type": "stdio",
      "command": "npx",
      "args": [
        "@playwright/mcp@latest",
        "--browser", "firefox"
      ]
    }
  }
}
```

### Slow Motion (for debugging)

```json
{
  "servers": {
    "playwright-slow": {
      "type": "stdio",
      "command": "npx",
      "args": [
        "@playwright/mcp@latest"
      ],
      "env": {
        "PLAYWRIGHT_SLOW_MO": "500"
      }
    }
  }
}
```

## Available Operations

### Page Navigation
- Navigate to URL
- Go back/forward
- Reload page
- Wait for navigation
- Wait for load state

### Element Interaction
- Click elements
- Fill input fields
- Select options
- Check/uncheck boxes
- Hover over elements
- Double-click
- Right-click

### Data Extraction
- Get text content
- Extract attributes
- Get HTML
- Query selectors
- Evaluate JavaScript
- Extract table data

### Screenshots & PDFs
- Full page screenshots
- Element screenshots
- PDF generation
- Video recording

### Waiting & Synchronization
- Wait for selector
- Wait for timeout
- Wait for load state
- Wait for network idle
- Wait for specific conditions

### Form Handling
- Fill forms
- Submit forms
- Upload files
- Handle dialogs
- Handle popups

## Example Workflows

### Workflow 1: E-commerce Product Monitoring

```
1. Navigate to https://example-shop.com/laptops
2. Extract all product names and prices
3. Filter products under $1000
4. Save the results to a JSON file
5. Take screenshots of the top 3 products
```

### Workflow 2: Automated Testing

```
1. Go to https://myapp.com
2. Click on "Sign Up" button
3. Fill in the registration form with test data
4. Submit the form
5. Verify that success message appears
6. Take a screenshot of the confirmation page
```

### Workflow 3: News Aggregation

```
1. Visit https://news.ycombinator.com
2. Get the top 10 article titles
3. Visit each article link
4. Extract the main content
5. Generate a summary report
```

### Workflow 4: SEO Analysis

```
1. Navigate to https://example.com
2. Extract all meta tags
3. Get all heading tags (H1, H2, H3)
4. Check for alt text on images
5. Analyze page load performance
6. Generate an SEO report
```

### Workflow 5: Competitive Analysis

```
1. Visit competitor websites
2. Extract pricing information
3. Capture screenshots of key features
4. Compare with our product offerings
5. Generate comparison report
```

## Best Practices

### 1. Error Handling

```
When navigating to a page, always wait for the page to load completely before interacting
```

### 2. Selectors

```
Use data-testid attributes or aria-labels for more reliable element selection
```

### 3. Performance

```
Use network idle state for pages with dynamic content loading
```

### 4. Rate Limiting

```
Add delays between requests when scraping to avoid being blocked
```

### 5. Authentication

```
Handle cookies and session storage for authenticated sessions
```

## Common Selectors

### CSS Selectors
```css
button.submit          /* Button with class submit */
#login-form           /* Element with ID login-form */
input[type="email"]   /* Email input field */
.card:first-child     /* First card element */
div > p               /* Paragraph directly under div */
```

### Text Selectors
```
text=Login            /* Exact text match */
text=/log.*in/i       /* Regex match (case insensitive) */
```

### Accessibility Selectors
```
role=button           /* Button role */
label=Email Address   /* Form label */
```

## Troubleshooting

### Browser Installation Issues

```bash
# Reinstall browsers
npx playwright install --force

# Install system dependencies (Linux)
npx playwright install-deps
```

### Timeout Errors

Ask Copilot:
```
Increase timeout to 30 seconds when waiting for the element to appear
```

### Element Not Found

```
Wait for the selector to be visible before clicking
```

### Authentication Issues

```
Set cookies for authentication before navigating to protected pages
```

### Memory Issues

```bash
# Close browser contexts when done
# Use headless mode for better performance
```

## Advanced Features

### 1. Network Interception

Ask Copilot:
```
Monitor all API calls made when loading the page
```

### 2. Geolocation

```
Set geolocation to New York and visit weather.com
```

### 3. Device Emulation

```
Emulate iPhone 12 and visit the mobile website
```

### 4. Download Handling

```
Click the download button and save the file
```

### 5. Multiple Tabs

```
Open link in new tab and switch between tabs
```

### 6. Iframe Handling

```
Interact with elements inside an iframe
```

## Testing Scenarios

### Login Testing
```
Test login functionality with valid and invalid credentials
```

### Form Validation
```
Test all form validation rules on the registration page
```

### Responsive Design
```
Test the website on mobile, tablet, and desktop viewports
```

### Accessibility
```
Run accessibility checks on the main pages
```

### Performance
```
Measure page load time and resource loading
```

## Web Scraping Ethics

1. **Respect robots.txt**
   - Check and follow website robots.txt rules

2. **Rate Limiting**
   - Don't overload servers with rapid requests
   - Add delays between requests

3. **Terms of Service**
   - Review and comply with website ToS
   - Don't scrape if explicitly prohibited

4. **Copyright**
   - Respect intellectual property
   - Use scraped data responsibly

5. **Privacy**
   - Don't scrape personal information
   - Follow GDPR and privacy regulations

## Playwright vs Puppeteer vs Selenium

| Feature | Playwright | Puppeteer | Selenium |
|---------|-----------|-----------|----------|
| Browsers | Chrome, Firefox, Safari | Chrome only | All major |
| Speed | Fast | Fast | Moderate |
| API | Modern | Modern | Legacy |
| Auto-wait | Yes | No | Limited |
| Cross-platform | Yes | Yes | Yes |

## Useful Commands

### Browser Management
```javascript
// Launch browser
browser = await playwright.chromium.launch();

// Create context
context = await browser.newContext();

// New page
page = await context.newPage();
```

### Navigation
```javascript
await page.goto('https://example.com');
await page.goBack();
await page.reload();
```

### Element Interaction
```javascript
await page.click('button');
await page.fill('input[name="email"]', 'test@example.com');
await page.selectOption('select', 'option-value');
```

### Data Extraction
```javascript
const text = await page.textContent('.title');
const html = await page.innerHTML('.content');
const value = await page.getAttribute('a', 'href');
```

## Resources
- [Playwright Documentation](https://playwright.dev/)
- [Playwright MCP Server](https://github.com/microsoft/playwright-mcp)
- [Playwright API Reference](https://playwright.dev/docs/api/class-playwright)
- [Playwright Testing Best Practices](https://playwright.dev/docs/best-practices)
- [Playwright Examples](https://github.com/microsoft/playwright/tree/main/examples)
- [Web Scraping with Playwright](https://playwright.dev/docs/scraping)

## Example Scripts

### Extract Blog Posts
```
Go to https://blog.example.com, extract all blog post titles, dates, and URLs, and save to JSON
```

### Monitor Price Changes
```
Visit the product page daily, extract the price, and alert if it drops below $100
```

### Test User Flow
```
1. Login with credentials
2. Add product to cart
3. Proceed to checkout
4. Verify total amount
5. Take screenshot of confirmation
```

### Generate Site Report
```
1. Visit all pages in the sitemap
2. Check for broken links
3. Verify meta tags
4. Measure load times
5. Generate comprehensive report
```
