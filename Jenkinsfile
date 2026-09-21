pipeline {
    agent any

    // These names must match what you configured under
    // Manage Jenkins -> Tools (from your Week 5 setup)
    tools {
        jdk 'Java21'
        maven 'Maven3'
    }

    options {
        timestamps()
        skipDefaultCheckout(false)
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out source code...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Compiling the project...'
                bat 'mvn -B clean compile'
            }
        }

        stage('Test') {
            steps {
                echo 'Running unit tests...'
                bat 'mvn -B test'
            }
            post {
                always {
                    // Publishes JUnit results so Jenkins shows pass/fail trends
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging the jar...'
                bat 'mvn -B package -DskipTests'
            }
        }

        stage('Archive') {
            steps {
                echo 'Archiving build artifact...'
                archiveArtifacts artifacts: 'target/bank-demo.jar', fingerprint: true
            }
        }

        stage('Run Demo') {
            steps {
                echo 'Running the packaged application...'
                bat 'java -jar target\\bank-demo.jar'
            }
        }
    }

    post {
        success {
            echo '✅ Build, tests, and packaging all succeeded.'
        }
        failure {
            echo '❌ Pipeline failed. Check the stage logs above.'
        }
    }
}
