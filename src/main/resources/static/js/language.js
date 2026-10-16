function changeLanguage(lang) {
  localStorage.setItem("siteLanguage", lang);

  const isHi = (lang === 'hi');

  /* ================= HEADER + MENU ================= */
  const headerTitle = document.querySelector('.gov-title h3');
  const headerSub = document.querySelector('.gov-title p');

  // Top-level menu items in DOM order: Home, About, Dashboard, Reports (dropdown toggle), Contact Us
  const menuLinks = document.querySelectorAll('.menu > a, .menu > .menu-dropdown > a');
  // Item(s) inside the Reports dropdown: MIS
  const dropdownLinks = document.querySelectorAll('.menu .dropdown-panel a');
  const authLinks = document.querySelectorAll('.auth a');

  if (headerTitle && headerSub) {
    if (isHi) {
      headerTitle.textContent = "प्रधानमंत्री कृषि सिंचाई योजना 3.0 - एमआईएस";
      headerSub.textContent = "भूमि संसाधन विभाग | ग्रामीण विकास मंत्रालय | भारत सरकार";
    } else {
      headerTitle.textContent = "Watershed Development Component-Pradhan Mantri Krishi Sinchayee Yojana 3.0 - MIS";
      headerSub.textContent = "Department of Land Resources | MoRD | Govt. of India";
    }
  }

  const menuTextEn = ["Home", "About", "Dashboard", "Reports \u25BE", "Contact Us"];
  const menuTextHi = ["होम", "परिचय", "डैशबोर्ड", "रिपोर्ट्स \u25BE", "संपर्क करें"];
  const menuText = isHi ? menuTextHi : menuTextEn;
  menuLinks.forEach(function (link, i) {
    if (menuText[i] !== undefined) {
      link.textContent = menuText[i];
    }
  });

  if (dropdownLinks.length > 0) {
    dropdownLinks[0].textContent = isHi ? "एमआईएस" : "MIS";
  }

  if (authLinks.length >= 2) {
    authLinks[0].textContent = isHi ? "लॉग इन" : "Login";
    authLinks[1].textContent = isHi ? "पंजीकरण" : "Registration";
  }

  /* ================= HOME PAGE ================= */
  const tagline = document.querySelector('.hero-content .tagline');
  const aboutBtn = document.querySelector('.hero-content .btn-delayed');
  const kpiLinks = document.querySelectorAll('.kpi-card .kpi-title');
  const Titles = document.querySelectorAll('.media-section .section-title');
  const scrollDown = document.querySelector('.scroll-down');

  if (tagline) {
    tagline.textContent = isHi
      ? "पीएमकेएसवाई 3.0 डिजिटल वाटरशेड मिशन"
      : "PMKSY 3.0 DIGITAL WATERSHED MISSION";
  }

  if (aboutBtn) {
    aboutBtn.textContent = isHi
      ? "डब्ल्यूडीसीपीएमकेएसवाई के बारे में"
      : "About WDCPMKSY";
  }

  if (kpiLinks.length > 0) {
    kpiLinks[0].textContent = isHi
      ? "मिट्टी और नमी संरक्षण गतिविधियों के अंतर्गत आने वाला क्षेत्र (हेक्टेयर में)"
      : "Area Covered with Soil and Moisture Conservation Activities (in ha.)";

    kpiLinks[1].textContent = isHi
      ? "पौधरोपण (बागवानी और वनीकरण) के अंतर्गत लाया गया क्षेत्र (हेक्टेयर में)"
      : "Area Brought under Plantation (Horticulture and Afforestation) (in ha.)";

    kpiLinks[2].textContent = isHi
      ? "नए बनाए गए और पुनर्जीवित किए गए जल संचयन ढांचे (संख्या में)"
      : "Water Harvesting Structure newly created and rejuvenated (in no.)";

    kpiLinks[3].textContent = isHi
      ? "सुरक्षित सिंचाई के अंतर्गत लाया गया अतिरिक्त क्षेत्र (हेक्टेयर में)"
      : "Additional Area brought under Protective Irrigation (in ha.)";

    kpiLinks[4].textContent = isHi
      ? "रोज़गार सृजन (मैन-डेज़ में)"
      : "Employment Generated (in mandays)";

    kpiLinks[5].textContent = isHi
      ? "लाभान्वित किसान (संख्या में)"
      : "Farmers Benefitted (in No.)";

    kpiLinks[6].textContent = isHi
      ? "सुधारी गई खराब ज़मीन का क्षेत्र और बारिश पर निर्भर विकसित क्षेत्र (हेक्टेयर में)"
      : "Area of Degraded Land covered and Rainfed area developed (in ha.)";
  }

  if (Titles.length > 1) {
    Titles[0].textContent = isHi ? "वाटरशेड फोटो गैलरी" : "Watershed Photo Gallery";
    Titles[1].textContent = isHi ? "सरकारी पोर्टल्स" : "Government Portals";
  }

  if (scrollDown) {
    scrollDown.textContent = isHi
      ? "↓ और जानने के लिए स्क्रॉल करें"
      : "↓ Scroll to Explore";
  }

  /* ================= REGISTRATION PAGE ================= */
  const regHeader = document.querySelector('.card-header h5');
  if (regHeader) {
    regHeader.textContent = isHi
      ? "उपयोगकर्ता पंजीकरण - WDC PMKSY 3.0 एमआईएस"
      : "User Registration - WDC PMKSY 3.0 MIS";
  }

  const accountTypeTitle = document.getElementById('accountTypeTitle');
  if (accountTypeTitle) {
    accountTypeTitle.textContent = isHi ? "खाता प्रकार" : "Account Type";
  }

  const userTypeLabel = document.getElementById('userTypeLabel');
  if (userTypeLabel) {
    userTypeLabel.textContent = isHi ? "उपयोगकर्ता प्रकार *" : "User Type *";
  }

  const location = document.getElementById('location');
  if (location) {
    location.textContent = isHi ? "स्थान की जानकारी *" : "Location Details *";
  }

  // State label
  const stateLabel = document.getElementById('state');
  if (stateLabel) {
    stateLabel.textContent = isHi ? "राज्य *" : "State *";
  }

  // District label
  const districtLabel = document.querySelector('#districtBox label');
  if (districtLabel) {
    districtLabel.textContent = isHi ? "जिला *" : "District *";
  }

  // Organization section
  const orgTitle = document.getElementById('orgSectionTitle');
  if (orgTitle) {
    orgTitle.textContent = isHi ? "संगठन का विवरण  *" : "Organization Details  *";
  }

  // Other labels
  const nameLabel = document.querySelector('#nameField label');
  if (nameLabel) {
    nameLabel.textContent = isHi ? "नाम *" : "Name *";
  }

  const deptLabel = document.querySelector('#deptField label');
  if (deptLabel) {
    deptLabel.textContent = isHi ? "विभाग *" : "Department *";
  }

  const desgLabel = document.querySelector('#desgField label');
  if (desgLabel) {
    desgLabel.textContent = isHi ? "पदनाम *" : "Designation *";
  }

  const ngoNameLabel = document.querySelector('#ngoNameField label');
  if (ngoNameLabel) {
    ngoNameLabel.textContent = isHi ? "एनजीओ का नाम *" : "NGO Name *";
  }

  const ngoLabel = document.querySelector('#ngoIdField label');
  if (ngoLabel) {
    ngoLabel.textContent = isHi ? "एनजीओ आईडी *" : "NGO ID *";
  }

  const ngoRegistered = document.querySelector('#ngoRegField label');
  if (ngoRegistered) {
    ngoRegistered.textContent = isHi ? "इसके साथ पंजीकृत *" : "Registered With *";
  }

  // Contact section
  const contactTitle = document.getElementById('contact');
  if (contactTitle) {
    contactTitle.textContent = isHi ? "संपर्क विवरण  *" : "Contact Details  *";
  }

  const emailLabel = document.querySelector('#emailField label');
  if (emailLabel) {
    emailLabel.textContent = isHi ? "ईमेल *" : "Email *";
  }

  const mobileLabel = document.querySelector('#mobileField label');
  if (mobileLabel) {
    mobileLabel.textContent = isHi ? "गतिमान *" : "Mobile *";
  }

  const addressLabel = document.querySelector('#addressField label');
  if (addressLabel) {
    addressLabel.textContent = isHi ? "पता *" : "Address *";
  }

  /* ================= CONTACT US PAGE ================= */

  // Header title
  const appTitle = document.querySelector('.app-title');
  if (appTitle) {
    appTitle.textContent = isHi ? "डब्ल्यूडीसी-पीएमकेएसवाई 3.0" : "WDC-PMKSY 3.0";
  }

  const appSubtitle = document.querySelector('.app-subtitle');
  if (appSubtitle) {
    appSubtitle.textContent = isHi
      ? "वाटरशेड विकास घटक\nप्रधानमंत्री कृषि सिंचाई योजना"
      : "Watershed Development Component\nPradhan Mantri Krishi Sinchayee Yojana";
  }

  // Only rename the first <h2> on the Contact Us page (it has a .right-section).
  // Previously this ran on every page and renamed the login page heading too.
  const contactHeading = document.querySelector('.right-section')
    ? document.querySelector('h2')
    : null;
  if (contactHeading) {
    contactHeading.textContent = isHi ? "संपर्क करें" : "CONTACT US";
  }

  // Email Support card
  const emailSupportLabel = document.getElementById('emailSupportLabel');
  if (emailSupportLabel) {
    emailSupportLabel.textContent = isHi ? "📧 ईमेल सहायता" : "📧 Email Support";
  }

  const emailSupportText = document.getElementById('emailSupportText');
  if (emailSupportText) {
    emailSupportText.textContent = "support-wdcpmksy[at]nic[dot]in"; // same in both languages
  }

  // Escalation Notice
  const noticeLabel = document.getElementById('noticeLabel');
  if (noticeLabel) {
    noticeLabel.textContent = isHi ? "⚠ महत्वपूर्ण सूचना" : "⚠ Important Notice";
  }

  const noticeText = document.getElementById('noticeText');
  if (noticeText) {
    noticeText.textContent = isHi
      ? "यदि समस्या कुछ दिनों बाद भी बनी रहती है,"
      : "If problem remains unresolved after a few days,";
  }

  const escalationEmail = document.getElementById('escalationEmail');
  if (escalationEmail) {
    escalationEmail.textContent = isHi
      ? "संपर्क करें: officer1.lris-2[at]nic[dot]in"
      : "contact: officer1.lris-2[at]nic[dot]in";
  }

  // Right section
  const rightHeading = document.querySelector('.right-section h2');
  if (rightHeading) {
    rightHeading.innerHTML = isHi
      ? "हम यहाँ हैं <span>आपकी मदद करने के लिए!</span>"
      : "WE ARE HERE TO <span>HELP YOU!</span>";
  }

  const subtitle = document.querySelector('.right-section .subtitle');
  if (subtitle) {
    subtitle.textContent = isHi
      ? "हमारी सहायता टीम हमेशा आपकी मदद के लिए तैयार है।"
      : "Our support team is always ready to assist you.";
  }

  // Officer 1
  const officer1Name = document.querySelector('.team-card:nth-of-type(1) h3');
  if (officer1Name) {
    officer1Name.textContent = isHi ? "👨‍💼 श्री अजय माधुकर जोशी" : "👨‍💼 Sh. Ajay Madhukar Joshi";
  }

  const officer1Role = document.querySelector('.team-card:nth-of-type(1) p');
  if (officer1Role) {
    officer1Role.textContent = isHi
      ? "उप महानिदेशक एवं समूह प्रमुख, एलआरआईएसडी, एनआईसी"
      : "DDG & Head of Group, LRISD, NIC";
  }

  // Officer 2
  const officer2Name = document.querySelector('.team-card:nth-of-type(2) h3');
  if (officer2Name) {
    officer2Name.textContent = isHi ? "👨‍💼 श्री गणेश खडंगा" : "👨‍💼 Sh. Ganesh Khadanga";
  }

  const officer2Role = document.querySelector('.team-card:nth-of-type(2) p');
  if (officer2Role) {
    officer2Role.textContent = isHi
      ? "वरिष्ठ तकनीकी निदेशक-प्रमुख, एलआरआईएसडी, एनआईसी"
      : "Senior Technical Director-HoD, LRISD, NIC";
  }

  // Officer 3
  const officer3Name = document.querySelector('.team-card:nth-of-type(3) h3');
  if (officer3Name) {
    officer3Name.textContent = isHi ? "👩‍💼 सुश्री ओमलता" : "👩‍💼 Ms. Omlata";
  }

  const officer3Role = document.querySelector('.team-card:nth-of-type(3) p');
  if (officer3Role) {
    officer3Role.textContent = isHi
      ? "वैज्ञानिक डी, एनआईसी-डोलर कंप्यूटर सेल"
      : "Scientist D, NIC-DoLR Computer Cell";
  }

  /* ================= LOGIN PAGE ================= */
  const loginLabel = document.getElementById('loginName');
  if (loginLabel) {
    loginLabel.textContent = isHi ? "लॉग इन करें" : "Login";
  }
}

document.addEventListener("DOMContentLoaded", () => {
  const savedLang = localStorage.getItem("siteLanguage") || "en";

  // Apply translations
  changeLanguage(savedLang);

  // Restore dropdown selection
  const langSelect = document.getElementById("languageSelect");
  if (langSelect) {
    langSelect.value = savedLang;
  }
});

let currentSize = 100; // percentage

function changeFontSize(action) {
  if (action === 'increase') {
    currentSize += 10;
  } else if (action === 'decrease') {
    currentSize -= 10;
  } else if (action === 'normal') {
    currentSize = 100;
  }
  document.documentElement.style.fontSize = currentSize + '%';
}