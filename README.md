
# PostgreSQL Setup Guide

This guide will walk you through setting up PostgreSQL for your application using Docker. It covers two scenarios:
1. **Docker is installed** and running on your machine.
2. **Docker is not installed**, and you need to install or configure it.

---
## 1. **Docker Installed and Running**

### Step 1: Ensure Docker is Running

Before proceeding, ensure that Docker is installed and running on your machine.

1. **Check if Docker is running**:
   Open a terminal (PowerShell or Command Prompt) and run:

   ```bash
   docker info
   ```

   If Docker is running, you should see detailed information about the Docker engine. If you receive an error, Docker may not be running or not properly configured.


2. **Start Docker Desktop**:
   If Docker is not running, ensure Docker Desktop is started:
    - Open **Docker Desktop** from the Start Menu.
    - Wait for it to fully initialize (you should see the Docker icon in the system tray).

### Step 2: Run PostgreSQL in Docker

Once Docker is running, you can launch a PostgreSQL container.

Navigate to src/main/resources and execute these commands.

```bash
docker  build -t user-management-db .
```
```bash
docker run --name task-container -e POSTGRES_PASSWORD=bar  -p 5432:5432 -d user-management-db
```

This will:
- Start a PostgreSQL container named `task-container`.
- Set the PostgreSQL username (`foo`), password (`bar`), and database (`user_management`).
- Expose port `5432` to interact with the PostgreSQL database.
- Run it in the background (`-d`).

**Jump to Step 3.**

---

# 2. **Docker Not Installed**

If Docker is not installed on your machine, follow these steps to install and configure it.

### Step 1: Install Docker Desktop

#### For Windows (with WSL 2)

1. **Download Docker Desktop**:
    - Visit the [Docker Desktop download page](https://www.docker.com/products/docker-desktop) and download the latest version for Windows.

2. **Install Docker Desktop**:
    - Run the installer and follow the on-screen instructions.
    - During installation, ensure that **WSL 2** (Windows Subsystem for Linux version 2) is enabled.

3. **Enable WSL 2**:
   If you don’t have WSL 2 installed, Docker Desktop will prompt you to install it. Follow these steps to install WSL 2:

    - Open **PowerShell** as Administrator and run the following commands to install WSL:

      ```bash
      wsl --install
      ```

    - Restart your computer after installation.

4. **Launch Docker Desktop**:
   After installation, search for **Docker Desktop** in the Start Menu and open it. Wait for Docker to start and fully initialize.

### Step 2: Create docker image

Navigate to src/main/resources and execute these commands.

```bash
docker build -t user-management-db .
```
```bash
docker run --name task-container -e POSTGRES_PASSWORD=bar  -p 5432:5432 -d user-management-db
```
---
# 3. Start the application
### 1. Navigate to root and execute:
```bash
./gradlew cleanBuild
```
### 2. run the application - src/main/java/com/slaxation/moro/KotlinMoroApplication.java

#  4. Testing

### To test this (using e.g. Postman) use:
Basic Authorization

## Create User:
- POST
- Unauthorized
```
localhost:8080/users/create
```
Body:
```
{
    "username": "user1",
    "name": null,
    "password": "test1"
}
```

## Get by Id:
- GET
- Authorization - Basic
    - **ussername** - admin
    - **password** -  test

```
localhost:8080/users/id/{id}
```
## Get All:
- GET
- Authorization - Basic
    - **ussername** - admin
    - **password** -  test

```
localhost:8080/users/all
```
## Update:
- POST
- Authorization - Basic
    - **ussername** - admin
    - **password** -  test

```
localhost:8080/users/update
```
Body:
```
{
  "username": "admin",
  "password": "test1",
  "name": "admin"
}
```

## Self delete:
- DELETE
- Authorization - Basic
- here - keep in mind you can have different credentials after executing previous steps
    - **ussername** - admin
    - **password** -  test
```
localhost:8080/users/self-delete
```







