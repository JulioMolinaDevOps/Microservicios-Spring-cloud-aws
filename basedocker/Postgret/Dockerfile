FROM postgres:14

ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=1234
ENV POSTGRES_DB=mscv_usuarios

EXPOSE 5433

COPY mscv-usuarios.sql /docker-entrypoint-initdb.d/