--
-- PostgreSQL database dump
--

\restrict xJCztofcjfH55dCGjhfy3AvrmG92etQWstUehLtobvVo5BM5es3gWHNENagy5iB

-- Dumped from database version 15.15
-- Dumped by pg_dump version 15.15

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: clientes; Type: TABLE; Schema: public; Owner: bank123
--

CREATE TABLE public.clientes (
    id_cliente integer NOT NULL,
    nome_completo character varying(100) NOT NULL,
    cpf character varying(14) NOT NULL,
    email character varying(150),
    data_nascimento date,
    endereco_logradouro character varying(150),
    endereco_numero character varying(20),
    endereco_complemento character varying(50),
    endereco_bairro character varying(50),
    endereco_cidade character varying(100),
    endereco_estado character varying(255),
    endereco_cep character varying(9),
    data_atualizacao timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    numeroconta integer NOT NULL
);


ALTER TABLE public.clientes OWNER TO bank123;

--
-- Name: clientes_id_cliente_seq; Type: SEQUENCE; Schema: public; Owner: bank123
--

CREATE SEQUENCE public.clientes_id_cliente_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.clientes_id_cliente_seq OWNER TO bank123;

--
-- Name: clientes_id_cliente_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: bank123
--

ALTER SEQUENCE public.clientes_id_cliente_seq OWNED BY public.clientes.id_cliente;


--
-- Name: contas; Type: TABLE; Schema: public; Owner: bank123
--

CREATE TABLE public.contas (
    numeroconta integer NOT NULL,
    datacriacao timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    saldo numeric(15,2) NOT NULL
);


ALTER TABLE public.contas OWNER TO bank123;

--
-- Name: livrocaixa; Type: TABLE; Schema: public; Owner: bank123
--

CREATE TABLE public.livrocaixa (
    idtransacao integer NOT NULL,
    datatransacao timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    valortransacao numeric(15,2) NOT NULL,
    numeroconta integer NOT NULL,
    operacao character varying(10),
    destino character varying(255),
    origem character varying(255),
    CONSTRAINT livrocaixa_operacao_check CHECK (((operacao)::text = ANY ((ARRAY['ENTRADA'::character varying, 'SAIDA'::character varying])::text[])))
);


ALTER TABLE public.livrocaixa OWNER TO bank123;

--
-- Name: livrocaixa_idtransacao_seq; Type: SEQUENCE; Schema: public; Owner: bank123
--

CREATE SEQUENCE public.livrocaixa_idtransacao_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.livrocaixa_idtransacao_seq OWNER TO bank123;

--
-- Name: livrocaixa_idtransacao_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: bank123
--

ALTER SEQUENCE public.livrocaixa_idtransacao_seq OWNED BY public.livrocaixa.idtransacao;


--
-- Name: clientes id_cliente; Type: DEFAULT; Schema: public; Owner: bank123
--

ALTER TABLE ONLY public.clientes ALTER COLUMN id_cliente SET DEFAULT nextval('public.clientes_id_cliente_seq'::regclass);


--
-- Name: livrocaixa idtransacao; Type: DEFAULT; Schema: public; Owner: bank123
--

ALTER TABLE ONLY public.livrocaixa ALTER COLUMN idtransacao SET DEFAULT nextval('public.livrocaixa_idtransacao_seq'::regclass);


--
-- Data for Name: clientes; Type: TABLE DATA; Schema: public; Owner: bank123
--

COPY public.clientes (id_cliente, nome_completo, cpf, email, data_nascimento, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade, endereco_estado, endereco_cep, data_atualizacao, numeroconta) FROM stdin;
1	Fabio Pereira	123.456.789-00	fabio.pereira@bank123.com	1979-04-20	Av. Paulista	1000	\N	Bela Vista	São Paulo	SP	01310-100	2025-12-13 10:04:07.784694	123456
\.


--
-- Data for Name: contas; Type: TABLE DATA; Schema: public; Owner: bank123
--

COPY public.contas (numeroconta, datacriacao, saldo) FROM stdin;
123456	2025-12-13 10:04:07.770759	1000.00
\.


--
-- Data for Name: livrocaixa; Type: TABLE DATA; Schema: public; Owner: bank123
--

COPY public.livrocaixa (idtransacao, datatransacao, valortransacao, numeroconta, operacao, destino, origem) FROM stdin;
1	2025-12-13 10:04:07.772058	60.00	123456	SAIDA	Fabio Pereira	Tatiana Favoretti
\.


--
-- Name: clientes_id_cliente_seq; Type: SEQUENCE SET; Schema: public; Owner: bank123
--

SELECT pg_catalog.setval('public.clientes_id_cliente_seq', 1, true);


--
-- Name: livrocaixa_idtransacao_seq; Type: SEQUENCE SET; Schema: public; Owner: bank123
--

SELECT pg_catalog.setval('public.livrocaixa_idtransacao_seq', 1, true);


--
-- Name: clientes clientes_cpf_key; Type: CONSTRAINT; Schema: public; Owner: bank123
--

ALTER TABLE ONLY public.clientes
    ADD CONSTRAINT clientes_cpf_key UNIQUE (cpf);


--
-- Name: clientes clientes_pkey; Type: CONSTRAINT; Schema: public; Owner: bank123
--

ALTER TABLE ONLY public.clientes
    ADD CONSTRAINT clientes_pkey PRIMARY KEY (id_cliente);


--
-- Name: contas contas_pkey; Type: CONSTRAINT; Schema: public; Owner: bank123
--

ALTER TABLE ONLY public.contas
    ADD CONSTRAINT contas_pkey PRIMARY KEY (numeroconta);


--
-- Name: livrocaixa livrocaixa_pkey; Type: CONSTRAINT; Schema: public; Owner: bank123
--

ALTER TABLE ONLY public.livrocaixa
    ADD CONSTRAINT livrocaixa_pkey PRIMARY KEY (idtransacao);


--
-- Name: idx_clientes_numeroconta; Type: INDEX; Schema: public; Owner: bank123
--

CREATE INDEX idx_clientes_numeroconta ON public.clientes USING btree (numeroconta);


--
-- Name: livrocaixa fk_conta; Type: FK CONSTRAINT; Schema: public; Owner: bank123
--

ALTER TABLE ONLY public.livrocaixa
    ADD CONSTRAINT fk_conta FOREIGN KEY (numeroconta) REFERENCES public.contas(numeroconta);


--
-- Name: clientes fk_conta_cliente; Type: FK CONSTRAINT; Schema: public; Owner: bank123
--

ALTER TABLE ONLY public.clientes
    ADD CONSTRAINT fk_conta_cliente FOREIGN KEY (numeroconta) REFERENCES public.contas(numeroconta) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

\unrestrict xJCztofcjfH55dCGjhfy3AvrmG92etQWstUehLtobvVo5BM5es3gWHNENagy5iB

