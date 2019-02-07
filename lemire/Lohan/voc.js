function getRandomKey(o) {
    var tmpList = Object.keys(o);
    return tmpList[Math.floor(Math.random() * tmpList.length)];
}


function shuffle(o){ //v1.0
    for(var j, x, i = o.length; i; j = Math.floor(Math.random() * i), x = o[--i], o[i] = o[j], o[j] = x);
    return o;
};

function shuffleWords (w) {
    var a = w.split(" "),
        n = a.length;

    for (var i = 0; i < n; i++) {
        a[i] = a[i].split('').sort(function(){return 0.5-Math.random()}).join('');
    }
    return a.join(" ");
}

var mots;
var howmany;
var spacetrimmer;
var maxseconde;

function getFrenchVoice() {
          var voices = window.speechSynthesis.getVoices();
          if(voices.length == 0) return; // not ready yet
    	  var frenchvoices = voices.filter(function(voice) {
    	   return voice.lang.lastIndexOf('fr',0) === 0; });
    	  if(frenchvoices.length > 0) {
    	    return frenchvoices[0];
    	  } else {
    	    document.getElementById('msg').innerHTML = "Sorry: I do not speak French!";
    	  }
}

function showBoxes() {
        defineMots();
        document.getElementById('msg').innerHTML = "<em>L'ordinateur a choisi "+howmany+" mots et expressions dans une banque de "+mots.length+" mots.</em>";

        mots = shuffle(mots);
        var i;
        var out = document.getElementById("vocabulaire");
        out.innerHTML = "";
        var mycontent = "<ol '>"
        for (i = 0; i < howmany; i++) {
            var mot = mots[i];
            mots[i] = mots[i].trim().replace(spacetrimmer, " ");
            mycontent = mycontent + '<li style="margin-bottom:0;margin-top:0;"><input type="submit" id="speakbut' + i + '" value="Parle!" onclick=\'javascript:speak(' + i  + ');\' /><input type="text" autocorrect="off" autocomplete="off" spellcheck="false" name="lname" id="rep' + i + '" onkeypress=\'javascript:if(event.keyCode==13){checkval(' + i + ');}\'/> <input type="submit" id="but' + i + '" value="Soumettre" onclick=\'javascript:checkval(' + i + ');\' /> <span id="indice' + i + '" style="font-style: italic; color: grey;"></span> <span id="mauvais' + i + '" style="text-decoration: line-through; color: red;"></span></li>';
        }
        var mycontent = mycontent + "</ol>"
        out.innerHTML = mycontent;
        document.getElementById("rep" + (0)).focus();
        speak(0);

}

function getStarted() {
     if('speechSynthesis' in window) {
        spacetrimmer = new RegExp("\\s+", 'g');
        var watch = setInterval(function() {
          // Load all voices available
          var voicesAvailable = speechSynthesis.getVoices();
          if (voicesAvailable.length !== 0) {
            showBoxes();
            clearInterval(watch);
          }
        }, 100);
    } else {
    	   document.getElementById('msg').innerHTML = 'Ce navigateur ne supporte pas la génération de la voix. Cette page web ne fonctionnera pas.';
    }

}


function compare(str1, answer) {
    var t = str1.replace(spacetrimmer, " ").trim();
    return t === answer;
}




// Create a new utterance for the specified text and add it to
// the queue.
function speak(i) {
     try {
        window.speechSynthesis.cancel();
      // Create a new instance of SpeechSynthesisUtterance.
    	var msg = new SpeechSynthesisUtterance();
    	msg.text = mots[i];

      // Set the attributes.
    	msg.volume = 1.0;
    	msg.lang = 'fr';
    	msg.rate = 0.9;
    	var frenchvoice = getFrenchVoice();
    	if(frenchvoice) {
    	   msg.voice = frenchvoice;
    	}
      // Queue this utterance.
    	window.speechSynthesis.speak(msg);
     } catch (e) {
        alert(e);
     }
}

function checkval(i) {
    var answer = mots[i];
    if (document.getElementById("rep" + i).value.trim().length === 0) {
        return;
    }
    if (compare(document.getElementById("rep" + i).value, answer)) {
        document.getElementById("rep" + i).value = "Ok: " + document.getElementById("rep" + i).value;
        document.getElementById("rep" + i).disabled = true;
        document.getElementById("but" + i).disabled = true;
        while(document.getElementById("rep" + (i+1))) {
            if(document.getElementById("rep" + (i+1)).disabled) {
                i = i + 1;
            } else {
                document.getElementById("rep" + (i+1)).focus();
                speak(i+1);
                break;
            }
        }
    } else {
       document.getElementById("rep" + i).disabled = true;
       document.getElementById("mauvais" + i).innerHTML += " ("+document.getElementById("rep" + i).value+") ";  

       document.getElementById("but" + i).disabled = true;
       var previousval = document.getElementById("but" + i).value;
       document.getElementById("indice" + i).innerHTML = "Indice: " +shuffleWords (answer);
       var seconde = 0;
       document.getElementById("but" + i).value = maxseconde - seconde;
       var watch = setInterval(function() {
          ++seconde;
          document.getElementById("but" + i).value = maxseconde - seconde;
          if(seconde == maxseconde) {
            document.getElementById("but" + i).value = previousval;
            document.getElementById("but" + i).disabled = false;
            document.getElementById("rep" + i).disabled = false;
            clearInterval(watch);
          }
        }, 1000);
    }
}

window.onbeforeunload = function() {
    return "Si tu quittes la page, ton travail sera perdu.";
};
