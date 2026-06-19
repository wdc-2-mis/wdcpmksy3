function changeLanguage(lang) {
   localStorage.setItem("siteLanguage", lang);
  const headerTitle = document.querySelector('.gov-title h3');
  const headerSub = document.querySelector('.gov-title p');
  const menuLinks = document.querySelectorAll('.menu a');
  const menuLinks2 = document.querySelectorAll('.auth a');
  
    if (lang === 'hi') {
    headerTitle.textContent = "प्रधानमंत्री कृषि सिंचाई योजना 3.0 - एमआईएस";
    headerSub.textContent = "भूमि संसाधन विभाग | ग्रामीण विकास मंत्रालय | भारत सरकार";
    menuLinks[0].textContent = "होम";
    menuLinks[1].textContent = "परिचय";
    menuLinks[2].textContent = "डैशबोर्ड";
    menuLinks[3].textContent = "रिपोर्ट्स";
    menuLinks[4].textContent = "मानचित्र";
    menuLinks2[0].textContent = "लॉग इन";
    menuLinks2[1].textContent = "पंजीकरण";
 	  
   } else {
    headerTitle.textContent = "Watershed Development Component-Pradhan Mantri Krishi Sinchayee Yojana 3.0 - MIS";
    headerSub.textContent = "Department of Land Resources | MoRD | Govt. of India";
    menuLinks[0].textContent = "Home";
    menuLinks[1].textContent = "About";
    menuLinks[2].textContent = "Dashboard";
    menuLinks[3].textContent = "Reports";
    menuLinks[4].textContent = "Map";
    menuLinks2[0].textContent = "Login";
    menuLinks2[1].textContent = "Registration";
    
	  }
	  
	     //home page
	    const tagline = document.querySelector('.hero-content .tagline');
	    const aboutBtn = document.querySelector('.hero-content .btn-delayed');
	    const kpiLinks = document.querySelectorAll('.kpi-card .kpi-title');
	    const Titles = document.querySelectorAll('.media-section .section-title');
	    const scrollDown = document.querySelector('.scroll-down');
	    
	    if (tagline) {
	        tagline.textContent = lang === 'hi'
	          ? "पीएमकेएसवाई 3.0 डिजिटल वाटरशेड मिशन"
	          : "PMKSY 3.0 DIGITAL WATERSHED MISSION";
	      }	
	  	
	  	if (aboutBtn) {
	  	      aboutBtn.textContent = lang === 'hi'
	  	        ? "डब्ल्यूडीसीपीएमकेएसवाई के बारे में"
	  	        : "About WDCPMKSY";
	  	    }
	    
	  		if (kpiLinks.length > 0) {
	  		  kpiLinks[0].textContent = lang === 'hi'
	  		    ? "मिट्टी और नमी संरक्षण गतिविधियों के अंतर्गत आने वाला क्षेत्र (हेक्टेयर में)"
	  		    : "Area Covered with Soil and Moisture Conservation Activities (in ha.)";

	  		  kpiLinks[1].textContent = lang === 'hi'
	  		    ? "पौधरोपण (बागवानी और वनीकरण) के अंतर्गत लाया गया क्षेत्र (हेक्टेयर में)"
	  		    : "Area Brought under Plantation (Horticulture and Afforestation) (in ha.)";

	  		  kpiLinks[2].textContent = lang === 'hi'
	  		    ? "नए बनाए गए और पुनर्जीवित किए गए जल संचयन ढांचे (संख्या में)"
	  		    : "Water Harvesting Structure newly created and rejuvenated (in no.)";

	  		  kpiLinks[3].textContent = lang === 'hi'
	  		    ? "सुरक्षित सिंचाई के अंतर्गत लाया गया अतिरिक्त क्षेत्र (हेक्टेयर में)"
	  		    : "Additional Area brought under Protective Irrigation (in ha.)";

	  		  kpiLinks[4].textContent = lang === 'hi'
	  		    ? "रोज़गार सृजन (मैन-डेज़ में)"
	  		    : "Employment Generated (in mandays)";

	  		  kpiLinks[5].textContent = lang === 'hi'
	  		    ? "लाभान्वित किसान (संख्या में)"
	  		    : "Farmers Benefitted (in No.)";

	  		  kpiLinks[6].textContent = lang === 'hi'
	  		    ? "सुधारी गई खराब ज़मीन का क्षेत्र और बारिश पर निर्भर विकसित क्षेत्र (हेक्टेयर में)"
	  		    : "Area of Degraded Land covered and Rainfed area developed (in ha.)";
	  		}	
	  		
	  		if (Titles.length > 1) {
	  		  Titles[0].textContent = lang === 'hi'
	  		    ? "वाटरशेड फोटो गैलरी"
	  		    : "Watershed Photo Gallery";

	  		  Titles[1].textContent = lang === 'hi'
	  		    ? "सरकारी पोर्टल्स"
	  		    : "Government Portals";
	  		}

	  		if (scrollDown) {
	  		  scrollDown.textContent = lang === 'hi'
	  		    ? "↓ और जानने के लिए स्क्रॉल करें"
	  		    : "↓ Scroll to Explore";
	  		}  
	  
			// Registration page translations
			const regHeader = document.querySelector('.card-header h5');
			if (regHeader) {
			  regHeader.textContent = lang === 'hi'
			    ? "उपयोगकर्ता पंजीकरण - WDC PMKSY 3.0 एमआईएस"
			    : "User Registration - WDC PMKSY 3.0 MIS";
			}

			const accountTypeTitle = document.getElementById('accountTypeTitle');
			if (accountTypeTitle) {
			  accountTypeTitle.textContent = lang === 'hi'
			    ? "खाता प्रकार"
			    : "Account Type";
			}

			const userTypeLabel = document.getElementById('userTypeLabel');
			if (userTypeLabel) {
			  userTypeLabel.textContent = lang === 'hi'
			    ? "उपयोगकर्ता प्रकार *"
			    : "User Type *";
			}
			const location = document.getElementById('location');
						if (location) {
						  location.textContent = lang === 'hi'
						    ? "स्थान की जानकारी *"
						    : "Location Details *";
						}
			
			// State label
			const stateLabel = document.getElementById('state');
			if (stateLabel) {
			  stateLabel.textContent = lang === 'hi'
			    ? "राज्य *"
			    : "State *";
			}

			// District label
			const districtLabel = document.querySelector('#districtBox label');
			if (districtLabel) {
			  districtLabel.textContent = lang === 'hi'
			    ? "जिला *"
			    : "District *";
			}

			// Organization section
			const orgTitle = document.getElementById('orgSectionTitle');
			if (orgTitle) {
			  orgTitle.textContent = lang === 'hi'
			    ? "संगठन का विवरण  *"
			    : "Organization Details  *";
			}

			

			// Other labels
			const nameLabel = document.querySelector('#nameField label');
			if (nameLabel) {
			  nameLabel.textContent = lang === 'hi' ? "नाम *" : "Name *";
			}

			const deptLabel = document.querySelector('#deptField label');
			if (deptLabel) {
			  deptLabel.textContent = lang === 'hi' ? "विभाग *" : "Department *";
			}

			const desgLabel = document.querySelector('#desgField label');
			if (desgLabel) {
			  desgLabel.textContent = lang === 'hi' ? "पदनाम *" : "Designation *";
			}
			const ngoNameLabel = document.querySelector('#ngoNameField label');
			if (ngoNameLabel) {
			ngoNameLabel.textContent = lang === 'hi' ? "एनजीओ का नाम *" : "NGO Name *";
									}

			const ngoLabel = document.querySelector('#ngoIdField label');
			if (ngoLabel) {
				ngoLabel.textContent = lang === 'hi' ? "एनजीओ आईडी *" : "NGO ID *";
						}
			const ngoRegistered = document.querySelector('#ngoRegField label');
			if (ngoRegistered) {
				ngoRegistered.textContent = lang === 'hi' ? "इसके साथ पंजीकृत *" : "Registered With *";
							}			
			// Contact section
			const contactTitle = document.getElementById('contact');
			if (contactTitle) {
				contactTitle.textContent = lang === 'hi'
				? "संपर्क विवरण  *"
				: "Contact Details  *";
			}
						
			const emailLabel = document.querySelector('#emailField label');
			if (emailLabel) {
						  emailLabel.textContent = lang === 'hi' ? "ईमेल *" : "Email *";
						}

			const mobileLabel = document.querySelector('#mobileField label');
			if (mobileLabel) {
						  mobileLabel.textContent = lang === 'hi' ? "गतिमान *" : "Mobile *";
						}

						const addressLabel = document.querySelector('#addressField label');
						if (addressLabel) {
						  addressLabel.textContent = lang === 'hi' ? "पता *" : "Address *";
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

