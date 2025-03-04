# Use the official PostgreSQL image
FROM postgres:latest

# Set default environment variables (you can override these at runtime)
ENV POSTGRES_USER=foo
ENV POSTGRES_DB=user_management

# Copy initialization script into the container
COPY init.sql /docker-entrypoint-initdb.d/

# Expose PostgreSQL port
EXPOSE 5432
