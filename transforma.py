#! /usr/bin/env python3

# Script to add google analytics to all the html pages

# It still will require some css and js. Remember to place them in the proper
# folders
import os, sys
import re

def find_html(path):
    """ finds files with one of the extensions in the extension list from path and returns
    the file paths """
    matches = []
    for root, _, filenames in os.walk(path):
        for filename in filenames:
            path = os.path.join(root, filename)
            if filename.endswith(".html"):
                matches.append(path)
    return matches

files = find_html(".")
for path in files:
    print("Processing %s"%path)
    with open(path) as f:
        contents = f.read();
    replaced = re.sub(r'</head>', r"""
<!-- adding cookies warning for google analytics.
Attention: this might break compatibility with older browsers. Bad luck!  -->
<link href="./theme/moistyle.css" rel="stylesheet">
<script src="theme/moiscripts.js"></script>
</head>""", contents)

    replaced = re.sub(r'<body>',r"""
<body>
  <div>
    <div id="cookiesbox">
        Aquest lloc web utilitza galetes (<i>cookies</i>) per a analitzar el seu ús
        amb les eines que ofereix <a href="https://www.google.com/analytics">Google Analytics</a>.
        En continuar navegant-hi, estàs acceptant-ho.
        <br/>
        <a class="btn btn-neutral" onclick="setUserAcceptsCookies();">
            Tancar
            <span class="fa fa-window-close"></span>
        </a>
    </div>

    <script>setCookiesboxVisibility();</script>
</div>
""", replaced)
    replaced = re.sub(r"""</p><p style="background:red; text-align:center;font-weight:bold;font-size:large;margin-left:30px;">Atenció: aquests continguts seran eliminats en breu. Si et cal fer còpia d'alguna pàgina, ara és un bon moment.""" , r"", replaced)

    replaced = re.sub(r"""<link href="http://apuntstecnics.herokuapp.com/feeds/all.atom.xml" type="application/atom+xml" rel="alternate" title="Apunts tècnics Atom Feed" />""",
                      r"", replaced)

    replaced = re.sub(r'</body>', r"""
<!-- Google Analytics -->
<script>
(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
    (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
                         m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
                         })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

ga('create', 'UA-41103782-3', 'auto');
ga('send', 'pageview');

</script>
<!-- End Google Analytics -->
</body>""", replaced)

    replaced = re.sub(r'\.\./category/', r'./', replaced)
    replaced = re.sub(r'\./category/', r'./', replaced)
    replaced = re.sub(r'\.\./tag/', r'./', replaced)
    replaced = re.sub(r'\./tag/', r'./', replaced)
    replaced = re.sub(r'\.\./theme/', r'./theme/', replaced)

    with open(path, "w") as f:
        f.write(replaced)


