const puppeteer = require('puppeteer');

function timeout(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
};

(async() => {
  const browser = await puppeteer.launch();
  console.log(await browser.version());
  const page = await browser.newPage();
  await page.goto('http://www.google.com', {waitUntil: 'networkidle2'});
  await timeout(5000)
  await page.pdf({path: 'page.pdf', format: 'A4'});
  const html = await page.content();
  console.log(html);
  await browser.close();
})();
