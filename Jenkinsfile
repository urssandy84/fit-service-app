pipeline {
    agent {
        docker {
            image 'maven:3-alpine'
            args '-v $HOME/.m2:/root/.m2'
        }
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Initial Tests') {
            parallel {
                stage('Unit Test') {
                    steps {
                        sh 'cd fit-service && mvn test'
                    }
                }
                stage('Code Quality') {
                    steps {
                        /*sh 'mvn sonar:sonar -Dsonar.organization=urssandy84-github -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=bf83ae8ffd39437e3b4f45e1f429c5e7b38f09bd'*/
                        sh 'mvn sonar:sonar -Dsonar.host.url-http://localhost:9000'
                    }
                }
            }
        }
        stage('Integration Tests') {
            parallel {
                stage('Test V0.0.1') {
                    steps {
                        sh 'mvn install'
                        sh 'cd fit-service-int-test-v100 && mvn test'
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                echo "Deploy!"
            }
        }
    }
}