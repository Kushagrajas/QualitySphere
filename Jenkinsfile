
pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven3'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out QualitySphere'
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                echo 'Running Selenium tests'
                bat 'mvn -B clean test'
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true,
                  testResults: 'target/surefire-reports/*.xml'
        }
    }
}
