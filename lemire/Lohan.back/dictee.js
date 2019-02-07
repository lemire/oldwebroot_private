
var phrases;
var remaining;
var errorlist;
var words;
var montreerreur;
function splitIntoSentences(str) {
    return str.match( /[^\.!\?]+[\.!\?]+/g );
}

function howManyWords(str) {
    return (str.match(/\b\S+\b/g) || []).length
}

function getAllMethods(object) {
    return Object.getOwnPropertyNames(object).filter(function(property) {
        return typeof object[property] == 'function';
    });
}

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
        defineDictee();
        phrases = splitIntoSentences(madictee);
        remaining = phrases.length;
        errorlist = "<ol>";

        var i;
        var out = document.getElementById("dictee");
        out.innerHTML = "";
        var mycontent = "<ol '>";
        words = 0;
        for (i = 0; i < phrases.length; i++) {
            words = words + howManyWords(phrases[0]);
            mycontent = mycontent + '<li style="margin-bottom:0;margin-top:0;"><input type="submit" id="speakbut' + i + '" value="Dicte la phrase!" onclick=\'javascript:speak(' + i  + ');\' /><br /><textarea  cols="40" rows="5" autocorrect="off" autocomplete="off" spellcheck="false" name="lname" id="rep' + i + '" onkeypress=\'javascript:if(event.keyCode==13){checkval(' + i + ');}\'></textarea> <input type="submit" id="but' + i + '" value="Soumettre" onclick=\'javascript:checkval(' + i + ');\' /><br /><p id="anal' + i + '"></p><input type="submit" id="again' + i + '" value="Essaie de nouveau cette phrase!" onclick=\'javascript:tryagain(' + i  + ');\' style=\'display:none;margin:5em;\'/></li>';
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





// Create a new utterance for the specified text and add it to
// the queue.
function speak(i) {
     try {
        window.speechSynthesis.cancel();
      // Create a new instance of SpeechSynthesisUtterance.
    	var msg = new SpeechSynthesisUtterance();
    	msg.text = phrases[i];
      
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

function tryagain(i) {
    document.getElementById("rep" + i).disabled = false;
    document.getElementById("but" + i).disabled = false;
    document.getElementById("rep" + i).focus();
    document.getElementById("anal" + i).innerHTML = "";
    document.getElementById("again" + i).style.display = 'none';
    speak(i);
}
function calculeLettre() {
     var v = parseInt(document.getElementById("nfautes").value);
     if(isNaN(v)) {
        alert("Veuillez écrire un nombre valable.");
        return;
     }
     var pourcentage =Math.ceil( v*100.0/words);
     var mresult = "";
     if(pourcentage < 4){
        mresult = "A (très satisfaisant)";
     } else if(pourcentage <=7) {
        mresult = "B (satisfaisant)";
     } else if(pourcentage <=10) {
        mresult = "C (acceptable)";
     } else if(pourcentage <= 14) {
        mresult = "D (peu satisfaisant)";
     } else {
        mresult = "E (insatisfaisant)";
     }
     document.getElementById("resultat").innerHTML="Vous avez fait "+pourcentage+"% d'erreurs ce qui vous donne un "+mresult+".";
}


function checkval(i) {
    var answer = phrases[i];
    var hisanswer = document.getElementById("rep" + i).value.trim();

    if(hisanswer.length + 10 < answer.length) {
       document.getElementById("anal" + i).innerHTML = "Essaie un peu plus fort!";
       return;
    } 
    document.getElementById("anal" + i).innerHTML = "";

    if (hisanswer.length === 0) {
        return;
    }
    var diff = JsDiff.diffChars(hisanswer,answer);
    var feedback = "";
    var counter = 0;
    var stupidlist = ["s","es","e","nt"];
    var reconstructed = "";
    var bogus = "bogusword";

    diff.forEach(function(part){
     var tv = part.value.trim();
     if(tv.length > 0) {
       if(part.added) counter = counter + 1;
       if(part.removed) counter = counter + 1;
       // green for additions, red for deletions
       // grey for common parts
       if(part.added) {
        reconstructed = reconstructed + part.value;
        if(stupidlist.indexOf(tv)>=0) {
            reconstructed = reconstructed + bogus;
        }
       } else if (part.removed){
        // nothing to do
       } else {
        reconstructed = reconstructed + part.value;
       }
       var color = part.added ? 'green' :
        part.removed ? 'red' : 'grey';
       var backcolor = part.added ? 'yellow' :
        part.removed ? 'black' : 'white';
     
       feedback = feedback + "<span style='color:"+color+";background:"+backcolor+";'>"+part.value+"</span>";
     }
    });
    var stupid = 0;
    var mystupidmatch = new RegExp("\\B"+bogus+"\\b", 'g');
    var mt = reconstructed.match(mystupidmatch);
    if(mt) {
        stupid = mt.length;
    }


    document.getElementById("rep" + i).disabled = true;
    document.getElementById("but" + i).disabled = true;
    if(counter > 0) {
            if(stupid > 1) {
               document.getElementById("anal" + i).innerHTML = "<strong>J'ai detecté "+stupid+" possibles erreurs d'accord (par ex., un s manquant). Est-ce que tu as bien fait attention? La plupart des fautes sont des fautes d'accord. Le plus important en français est d'apprendre à bien accorder les mots.</strong> "+feedback; 
            } else {
              document.getElementById("anal" + i).innerHTML = feedback;
            }
            document.getElementById("again" + i).style.display = 'inline';
            document.getElementById("again" + i).focus();
            errorlist = errorlist + "<li>"+feedback+"</li>";
            document.getElementById("again" + i).disabled = true;
            var previousval = document.getElementById("again" + i).value;
            var seconde = 0;
            var maxseconde = 5;
            document.getElementById("again" + i).value = maxseconde - seconde; 
            var watch = setInterval(function() {
              ++seconde;
              document.getElementById("again" + i).value = maxseconde - seconde;
              if(seconde == maxseconde) {
                document.getElementById("again" + i).value = previousval;
                document.getElementById("again" + i).disabled = false;
                clearInterval(watch);
              }
        }, 1000);
    } else {
            document.getElementById("anal" + i).innerHTML = "Bravo! C'est parfait!";
            remaining = remaining - 1;
            if(document.getElementById("rep" + (i+1))) {
              document.getElementById("rep" + (i+1)).focus();
              speak(i+1);
            }
            if(remaining == 0) {
                alert("Bravo! Tu as terminé ta dictée de phrases.");
                errorlist = errorlist + "</ol>";
                errorlist = errorlist + "<p>Il y avait  "+ words+" mots dans cette dictée.</p>";
                errorlist = errorlist + "<p>Combien d'erreurs avez-vous commises? </p>";
                errorlist = errorlist + '<input  type="number"  name="nfautes" id="nfautes" onkeypress=\'javascript:if(event.keyCode==13){calculeLettre();}\'/>'; 
                errorlist = errorlist + '<input type="submit"  value="Calcule mon résultat!" onclick=\'javascript:calculeLettre();\' />';
                errorlist = errorlist + "<div id='resultat'></div>";
                document.getElementById('erreurs').innerHTML = errorlist;
                if(montreerreur) {
                  document.getElementById('erreurs').style.display = 'block';
                  document.getElementById('erreurs').focus();
                } else {
                  document.getElementById('erreurs').style.display = 'none';
                }
            }
    }
}


window.onbeforeunload = function() {
    return "Si tu quittes la page, ton travail sera perdu.";
};
