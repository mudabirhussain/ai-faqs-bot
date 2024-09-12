# AI-Powered FAQ Bot for E-Commerce - Backend

This repository contains an AI-powered bot built to serve an imaginary e-commerce platform by answering Frequently Asked Questions (FAQs). The bot is designed with a specific business purpose in mind: to provide accurate and timely answers to customer queries, enhancing the overall user experience.

## Technologies Used

The following technologies were used to develop this AI-powered bot:

- **Java 21**: The latest version of Java, offering enhanced performance, including the use of:
  - **Virtual Threads**: For lightweight, high-concurrency processing.
  - **Records**: For simplifying immutable data carrier classes.
  - **JDBC Client**: For efficient interaction with the PGVector database.
- **Spring Boot 3.3**: A robust framework for building Java applications, used for structuring the backend.
- **Spring AI**: Seamless integration of AI capabilities within the Spring Boot ecosystem.
- **OpenAI's GPT-4**: The cutting-edge natural language model powering the bot's responses.
- **Docker**: Containerization for easy deployment and environment consistency.
- **PGVector Database**: A vector database used to store and retrieve embeddings for efficient querying.

## Features

- **AI-Powered Responses**: Uses OpenAI's GPT-4 model to generate intelligent and context-aware responses to user queries.
- **Business-Specific Focus**: Tailored to address FAQs related to the e-commerce domain, with a focus on improving customer support.
- **Scalable Architecture**: Built using Spring Boot and Docker, allowing for easy scaling and deployment.
- **Virtual Threads**: High-concurrency management using Java 21’s virtual threads to handle large numbers of concurrent requests efficiently.
- **Records for Data Handling**: Simplifies data structures, making the code more maintainable and concise.
- **JDBC Client**: Enables direct interaction with the PGVector database for fast and reliable data retrieval.
- **Vector-Based Search**: Utilizes PGVector for fast and relevant query results.

## Business Purpose

The AI-powered bot was built to cater to an e-commerce platform, where providing fast and accurate responses to FAQs is crucial for enhancing customer satisfaction and reducing manual support efforts. It is designed to handle common customer queries, providing instant and relevant answers.

## How to Run

1. **Install Java 21**: Ensure Java 21 is installed.

2. **Clone the Repository**:

   ```bash
   git clone https://github.com/your-repo/ai-powered-faq-bot.git
   cd ai-powered-faq-bot
   ```

3. **Set Environment Variables**:

   - `OPENAI_API_KEY`: Set this to your OpenAI API secret key.

4. **Install Docker**:

   - The Postgres database will be created and initialized on the first run via Docker Compose.

5. **Create Vector Table**:
   Run the following SQL commands in your Postgres database:

   ```sql
   CREATE EXTENSION IF NOT EXISTS vector;
   CREATE EXTENSION IF NOT EXISTS hstore;
   CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

   CREATE TABLE IF NOT EXISTS vector_store (
       id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
       content text,
       metadata json,
       embedding vector(1536)  -- 1536 is the default embedding size
   );

   CREATE INDEX ON vector_store USING HNSW (embedding vector_cosine_ops);
   ```

6. **Open the Project in IntelliJ**: Import the project in IntelliJ IDEA.

7. **Run Maven Clean and Install**:

   ```bash
   mvn clean install
   ```

8. **Run the Project**: The project runs on port `:8080`.
