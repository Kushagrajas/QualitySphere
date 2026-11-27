
pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven3'
        allure 'Allure'
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

        stage('Allure Report') {
            steps {
                allure([
                    includeProperties: false,
                    jdk: '',
                    results: [[path: 'target/allure-results']]
                ])
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
