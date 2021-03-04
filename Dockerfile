FROM clojure:lein

WORKDIR /tmp

COPY src src
COPY target target
COPY test test
COPY project.clj project.clj
COPY Procfile Procfile

EXPOSE 3000
CMD ["lein", "run"]