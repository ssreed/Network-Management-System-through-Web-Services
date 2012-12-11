=====================================================================================
Folder Description
=====================================================================================
Source Code folder = The folder that contains all source codes

=====================================================================================
System Perquisite
=====================================================================================
All platforms are assumed to have no firewall blockings

=====================================================================================
Application Server Setup (Settings for Windows Environment)
=====================================================================================
Step 1
Download and install Java JDK SE u7
http://www.oracle.com/technetwork/java/javase/downloads/index.html
Download and install glassfish from Oracle official website
http://www.oracle.com/technetwork/java/javaee/downloads/java-ee-sdk-6u3-jdk-7u1-downloads-523391.html

Step 2
Download and install Eclipse with JAVA EE Development
http://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/junosr1

Step 4
Copy and paste glassfish from domain folder in the source code to the glassfish install folder/domains

Step 3
Open Eclipse and install Glassfish Server Component
Perspective -> JAVA EE -> Server -> Mouse Left Click -> Add New Server -> add ... (Left side of server runtime environment) -> Search for Oracle Glassfish 3.1.2 -> Install

Step 5
Perspective -> JAVA EE -> Server -> Mouse Left Click -> Add New Server -> add -> specificy the Glassfish installation folder path and the glassfishdomain folder path

Step 7
Install netsnmp from 
http://www.net-snmp.org/download.html

Step 8 
Import the project from backend folder into Eclipse

Step 9
Run as -> on Server -> Server that is created by above instructions
Glassfish Server
Username: admin
Password: glafihpaword

=====================================================================================
Network Element Setup (Settings for Ubuntu Linux 12.10 32bits)
=====================================================================================
Step 1
Install necessary components (Thanks to Josh Lipps's documents)
sudo apt-get update && sudo apt-get install snmp snmpd snmp-mibs-downloader

Step 2
Make a copy of original snmpd configuration file
sudo cp /etc/snmp/snmpd.conf /etc/snmp/snmpd.conf.backup

Step 3
Copy the configuration file in the source code folder -> ne_config folder
sudo cp snmpd.conf /etc/snmp/snmpd.conf
sudo cp snmpd.conf /usr/share/snnmp/snmpd.conf

Step 4
Open the file /etc/default/snmpd and modifiy the line with SNMPDOPTS to
SNMPDOPTS='-Lsd -Lf /dev/null -u snmp -I -smux -p /var/run/snmpd.pid -c /etc/snmp/snmpd.conf'

Step 6
Reboot the system

Step 7
Start SNMP Agent
sudo service snmpd start

Step 8
Start the SNMP Adapter program in the souce folder -> snmpadapter
sudo java -jar snmpadapter.jar

=====================================================================================
Frontend (Platform Independent)
=====================================================================================
Step 1
Force the browser to accept the invalid certificate
Open browser and type in the address of server in the format
https://application_server_address:8181 
where the application_server_address is the address of application server

Step 2
The user now will be able to use the frontend through index.html
The read only community string
public
The read/write community string
cs158bwrite
The community string for enable/disable snmp agent and rmon commands
linux
The community that is associate with security name
Security name is cs158bsec
Community string is cs158bwrite_access_test

=====================================================================================
The end of documentation
=====================================================================================
Thank you for viewing
