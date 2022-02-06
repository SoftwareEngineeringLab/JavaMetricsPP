#!/bin/bash

# download files

wget -O csv-conform-1.0-SNAPSHOT-jar-with-dependencies.jar https://raw.githubusercontent.com/SoftwareEngineeringLab/JavaMetricsPP/25f24aceca1985fc78fccbc231aec8b16170560d/MLCQ-dataset/test/csv-conform-1.0-SNAPSHOT-jar-with-dependencies.jar;

wget -O metrics-calculator-1.0-SNAPSHOT-jar-with-dependencies.jar https://raw.githubusercontent.com/SoftwareEngineeringLab/JavaMetricsPP/25f24aceca1985fc78fccbc231aec8b16170560d/MLCQ-dataset/test/metrics-calculator-1.0-SNAPSHOT-jar-with-dependencies.jar;

wget -O MLCQ_small.csv https://raw.githubusercontent.com/SoftwareEngineeringLab/JavaMetricsPP/25f24aceca1985fc78fccbc231aec8b16170560d/MLCQ-dataset/test/MLCQ_small.csv;

# clone repositories
{
	read
	while IFS= read -r line; do
		IFS=';' read -ra ADDR <<< "$line"
		repository_address="${ADDR[8]}"
		revision="${ADDR[9]}"
		link="${ADDR[13]}"
		
		IFS='/' read -ra TAB <<< "$link"
		user_name="${TAB[3]}"
		project_name="${TAB[4]}"
		
		mkdir "${user_name}"
		cd "${user_name}"
		
		git clone "${repository_address}"
		
		cd "${project_name}"
		git reset --hard "${revision}"
		
		cd ..
		cd ..
	done
	
} < MLCQ_small.csv;


# calculate metrics
java -jar csv-conform-1.0-SNAPSHOT-jar-with-dependencies.jar MLCQ_small.csv MLCQ_conformed.csv;

java -jar metrics-calculator-1.0-SNAPSHOT-jar-with-dependencies.jar -csv $PWD/MLCQ_conformed.csv -gitsources $PWD;
