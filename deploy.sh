
#update packages
apt-get update -y
apt-get install -y gettext-base

#build the latest code changes.
mvn clean install -DskipTests -DoutputDirectory=/home/ubuntu/wise_economy/service/wise-economy-service/target

#add the service file to systemctl.
sudo cp /home/ubuntu/wise_economy/service/wise-economy-service/wise-economy.service /etc/systemd/system/

# Update the system-daemon
sudo systemctl daemon-reload

# stop the existing process
sudo service wise-economy restart

