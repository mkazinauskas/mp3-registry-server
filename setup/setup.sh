cd /root
yum -y remove httpd

#install java

cd /root

yum -y update

wget --no-cookies --no-check-certificate --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" "http://download.oracle.com/otn-pub/java/jdk/8u60-b27/jdk-8u60-linux-x64.rpm"

rpm -ivh jdk-8u60-linux-x64.rpm

#install mysql

wget http://repo.mysql.com/mysql-community-release-el7-5.noarch.rpm

sudo rpm -ivh mysql-community-release-el7-5.noarch.rpm

yum -y update

yum -y install mysql-server

systemctl start mysqld

#MYSQL_ROOT_PASSWORD=FuckingPasswordr12343

#echo -e "\ny\ny\n$MYSQL_ROOT_PASSWORD\n$MYSQL_ROOT_PASSWORD\ny\ny\ny\ny" | mysql_secure_installation

#https://www.linode.com/docs/databases/mysql/how-to-install-mysql-on-centos-7

#mysql -uroot -p$MYSQL_ROOT_PASSWORD -e "create schema mp3-register;"

#Add program to automatically start
systemctl enable register.service
systemctl enable search.service
systemctl enable gateway.service

chkconfig register --add
chkconfig register on

chkconfig search --add
chkconfig search on

chkconfig gateway --add
chkconfig gateway on

/sbin/reboot