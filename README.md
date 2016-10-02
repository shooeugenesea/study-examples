<h3>
Introduction</h3>
<div>
This branch is to practice Java SSL and note how to generate jks files
</div>
<h3>
Reference</h3>
<div>
<a href="http://www.programgo.com/article/71463377839/" target="_blank">http://www.programgo.com/article/71463377839/</a><br />
<a href="https://docs.oracle.com/cd/E19509-01/820-3503/jcapsconfssls_intro/index.html" target="_blank">https://docs.oracle.com/cd/E19509-01/820-3503/jcapsconfssls_intro/index.html</a></div>
<h3>
Prepare jks files</h3>
<div>
<ol>
<li>Install openssl in Ubuntu
<pre>sudo apt-get install openssl</pre>
</li>
<li>Execute following commands one by one
<pre>openssl req -x509 -newkey rsa:1024 -keyout private/cakey.pem -out private/cacert.pem -days 3650
openssl x509 -in private/cacert.pem -addtrust clientAuth -setalias "Isaac Test Class 1 CA" -out public/catrust.pem
keytool -importcert -trustcacerts -noprompt -file private/cacert.pem -alias ca -keystore public/catrust.jks -storepass 123456
keytool -genkeypair -keyalg RSA -keysize 1024 -validity 730 -keystore public/server.jks
keytool -certreq -file server-req.pem -keystore public/server.jks
openssl x509 -req -in server-req.pem -out public/server-cert.pem -CA private/cacert.pem -CAkey private/cakey.pem -extensions v3_usr -days 730 -CAserial private/cacert.srl -CAcreateserial
cat private/cacert.pem &gt;&gt; public/server-cert.pem
keytool -importcert -v -file public/server-cert.pem -keystore public/server.jks
rm server-req.pem</pre>
</li>
<li>There are public/server.jks and public/catrust.jks</li>
</ol>
</div>
