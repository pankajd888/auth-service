pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "auth-service"
        DOCKER_HUB_USER = "pankajd888"
    }

    triggers {
        githubPush() // auto-trigger on GitHub push
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Cloning repository..."
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo "Building Spring Boot application..."
                // ✅ Use mvnw.cmd for Windows
                bat 'mvnw.cmd clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo "Building Docker image..."
                    // ✅ Windows-friendly Docker build
                    bat "docker build -t ${DOCKER_HUB_USER}/${DOCKER_IMAGE}:latest ."
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    echo "Pushing Docker image to Docker Hub..."
                    withCredentials([string(credentialsId: 'dockerhub-token', variable: 'DOCKERHUB_TOKEN')]) {
                        // ✅ Use Windows syntax for token and login
                        bat """
                            echo %DOCKERHUB_TOKEN% > token.txt
                            docker login -u ${DOCKER_HUB_USER} --password-stdin < token.txt
                            docker push ${DOCKER_HUB_USER}/${DOCKER_IMAGE}:latest
                            del token.txt
                        """
                    }
                }
            }
        }
    }

    post {
        success {
            echo "✅ Build and Docker Image creation successful!"
        }
        failure {
            echo "❌ Build failed. Check the logs!"
        }
    }
}







// pipeline {
//   agent any

//   // Use Maven Wrapper if present
//   options {
//     timestamps()
//     disableConcurrentBuilds()
//     buildDiscarder(logRotator(numToKeepStr: '10'))
//   }

//   parameters {
//     choice(name: 'ENV', choices: ['dev', 'stage', 'prod'], description: 'Target environment')
//   }

//   environment {
//     APP_NAME = 'auth-service'
//     APP_PORT = '8081'
//     // default; will be overridden per-ENV in a stage
//     SPRING_PROFILES_ACTIVE = 'dev'
//     MAVEN_OPTS = '-Dmaven.test.failure.ignore=false'
//   }

//   tools {
//     // if Jenkins has a configured JDK tool; otherwise remove this and rely on agent image/tooling
//     jdk 'JDK17'
//   }

//   stages {
//     stage('Checkout') {
//       steps {
//         checkout scm
//         sh 'java -version || true'
//         sh '[ -f ./mvnw ] && ./mvnw -v || mvn -v'
//       }
//     }

//     stage('Set Environment') {
//       steps {
//         script {
//           if (params.ENV == 'dev')  { env.SPRING_PROFILES_ACTIVE = 'dev' }
//           if (params.ENV == 'stage'){ env.SPRING_PROFILES_ACTIVE = 'stage' }
//           if (params.ENV == 'prod') { env.SPRING_PROFILES_ACTIVE = 'prod' }
//           env.JAVA_OPTS = "-Dserver.port=${env.APP_PORT} -Dspring.profiles.active=${env.SPRING_PROFILES_ACTIVE}"
//           echo "Active profile: ${env.SPRING_PROFILES_ACTIVE}"
//         }
//       }
//     }

//     stage('Build') {
//       steps {
//         sh '[ -f ./mvnw ] && ./mvnw -B -DskipTests=true clean package || mvn -B -DskipTests=true clean package'
//       }
//     }

//     stage('Test') {
//       steps {
//         sh '[ -f ./mvnw ] && ./mvnw -B test || mvn -B test'
//       }
//       post {
//         always {
//           junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
//         }
//       }
//     }

//     // Optional: package again without skipping tests, or run integration tests if you have them
//     stage('Package (Verify)') {
//       steps {
//         sh '[ -f ./mvnw ] && ./mvnw -B -DskipTests=false verify || mvn -B -DskipTests=false verify'
//       }
//       post {
//         success {
//           archiveArtifacts artifacts: 'target/*.jar', onlyIfSuccessful: true
//         }
//       }
//     }
//   }

//   post {
//     success {
//       echo "Build ${env.BUILD_NUMBER} for ${APP_NAME} (${params.ENV}) succeeded."
//     }
//     failure {
//       echo "Build ${env.BUILD_NUMBER} failed. Check logs and test reports."
//     }
//   }
// }
