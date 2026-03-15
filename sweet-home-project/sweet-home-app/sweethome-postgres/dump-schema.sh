#!/bin/bash
docker exec sweethome-db pg_dump -U sweethome --schema-only --no-owner --no-privileges sweethome > schema.sql
echo "Database schema successfully dumped to schema.sql"
