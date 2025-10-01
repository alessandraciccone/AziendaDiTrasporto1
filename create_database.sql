-- Script per creare il database per l'azienda di trasporto

-- Controlla se il database esiste
SELECT datname FROM pg_database WHERE datname = 'azienda_di_trasporto';

-- Se non esiste, eseguire questo comando (scollegati da tutti i database prima):
-- CREATE DATABASE azienda_di_trasporto;

-- Oppure usa questo per eliminare e ricreare:
-- DROP DATABASE IF EXISTS azienda_di_trasporto;
-- CREATE DATABASE azienda_di_trasporto;

-- Dopo aver creato il database, connettiti ad esso e verifica le tabelle:
\c azienda_di_trasporto

-- Lista tutte le tabelle
SELECT table_name 
FROM information_schema.tables 
WHERE table_schema = 'public'
ORDER BY table_name;

