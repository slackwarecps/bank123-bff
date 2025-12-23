--
-- PostgreSQL database dump
--

\restrict aj3uDA4rM6cEvEBRiwBWIIMpD6269yZpXZRYYCbkOPoXUWSpaR7caBvPF8JSIAN

-- Dumped from database version 17.7
-- Dumped by pg_dump version 17.7 (Homebrew)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
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
-- Name: clientes; Type: TABLE; Schema: public; Owner: postgres
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


ALTER TABLE public.clientes OWNER TO postgres;

--
-- Name: clientes_id_cliente_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.clientes_id_cliente_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.clientes_id_cliente_seq OWNER TO postgres;

--
-- Name: clientes_id_cliente_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.clientes_id_cliente_seq OWNED BY public.clientes.id_cliente;


--
-- Name: contas_numeroconta_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.contas_numeroconta_seq
    START WITH 123457
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.contas_numeroconta_seq OWNER TO postgres;

--
-- Name: contas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.contas (
    numeroconta integer DEFAULT nextval('public.contas_numeroconta_seq'::regclass) NOT NULL,
    datacriacao timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    saldo numeric(15,2) NOT NULL,
    email character varying(150),
    id_user_firebase character varying(100),
    status character varying(20) DEFAULT 'ativa'::character varying
);


ALTER TABLE public.contas OWNER TO postgres;

--
-- Name: livrocaixa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.livrocaixa (
    idtransacao integer NOT NULL,
    datatransacao timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    valortransacao numeric(15,2) NOT NULL,
    numeroconta integer NOT NULL,
    operacao character varying(10),
    destino character varying(255),
    origem character varying(255),
    CONSTRAINT livrocaixa_operacao_check CHECK (((operacao)::text = ANY (ARRAY[('ENTRADA'::character varying)::text, ('SAIDA'::character varying)::text])))
);


ALTER TABLE public.livrocaixa OWNER TO postgres;

--
-- Name: livrocaixa_idtransacao_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.livrocaixa_idtransacao_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.livrocaixa_idtransacao_seq OWNER TO postgres;

--
-- Name: livrocaixa_idtransacao_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.livrocaixa_idtransacao_seq OWNED BY public.livrocaixa.idtransacao;


--
-- Name: posts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.posts (
    id integer NOT NULL,
    title character varying(255) NOT NULL,
    content text,
    created_at timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.posts OWNER TO postgres;

--
-- Name: posts_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.posts_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.posts_id_seq OWNER TO postgres;

--
-- Name: posts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.posts_id_seq OWNED BY public.posts.id;


--
-- Name: clientes id_cliente; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clientes ALTER COLUMN id_cliente SET DEFAULT nextval('public.clientes_id_cliente_seq'::regclass);


--
-- Name: livrocaixa idtransacao; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.livrocaixa ALTER COLUMN idtransacao SET DEFAULT nextval('public.livrocaixa_idtransacao_seq'::regclass);


--
-- Name: posts id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.posts ALTER COLUMN id SET DEFAULT nextval('public.posts_id_seq'::regclass);


--
-- Data for Name: clientes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.clientes (id_cliente, nome_completo, cpf, email, data_nascimento, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade, endereco_estado, endereco_cep, data_atualizacao, numeroconta) FROM stdin;
1	Fabio Pereira	123.456.789-00	teste@teste.com.br	1979-04-20	Av. Paulista	1000	Rua das bacias	Bela Vista	São Paulo	SP	01310-100	2025-12-13 10:04:07.784694	123471
\.


--
-- Data for Name: contas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.contas (numeroconta, datacriacao, saldo, email, id_user_firebase, status) FROM stdin;
123457	2025-12-20 22:46:19.541841	0.00	fabio.alvaro@gmail.com	123	ativa
123458	2025-12-20 22:46:54.173863	0.00	fabio.alvaro@gmail.com	123	ativa
123467	2025-12-21 14:16:02.86403	0.00	fabio.alvaro@gmail.com	UufzefbskVZKUFRI4sGn45Ekluu1	ativa
123466	2025-12-21 13:50:52.919089	50000.00	manga@teste.com.br	aCfLrhcPstP9f5CwNVIPtpvVxw53	ativa
123470	2025-12-21 16:38:26.467274	0.00	uva@teste.com.br	rVf0xL3cBGPscc8JmRJhewAxcIL2	ativa
123456	2025-12-13 10:04:07.770759	1000.00	teste222@teste.com.br	L8AcHrfkHtUB1AxsfBdlfkhaMXG2	ativa
123471	2025-12-21 22:04:16.259943	40000.00	teste@teste.com.br	qVi4nh7xsZfQUnk8Ii2MspwyrZb2	ativa
123472	2025-12-21 23:19:01.463748	0.00	limao@teste.com.br	S4SAvWbkt0Pu18v82svNfFXAkK73	ativa
123473	2025-12-21 23:48:29.799631	0.00	morango@teste.com.br	vJqdKBVVyyOwSPVNoqro9Ffwjzs1	ativa
\.


--
-- Data for Name: livrocaixa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.livrocaixa (idtransacao, datatransacao, valortransacao, numeroconta, operacao, destino, origem) FROM stdin;
1	2025-12-13 10:04:07.772058	60.00	123471	SAIDA	Fabio Pereira	Tatiana Favoretti
2	2025-12-21 19:32:53.141936	50.00	123456	ENTRADA	123	123
3	2025-12-21 19:44:41.657496	50.00	123456	SAIDA	123456	123456
4	2025-12-21 19:50:21.677585	50.00	123456	SAIDA	123456	123456
5	2025-12-21 19:50:48.232732	50.00	123456	SAIDA	123471	123456
6	2025-12-21 19:52:22.393931	50.00	123456	SAIDA	123471	123456
7	2025-12-21 19:52:29.774637	125.00	123456	SAIDA	123471	123456
8	2025-12-21 19:52:47.495671	125.00	123456	SAIDA	123471	123456
9	2025-12-21 19:52:48.721421	125.00	123456	SAIDA	123471	123456
10	2025-12-21 23:09:09.023249	127.00	123470	SAIDA	123471	123470
11	2025-12-21 23:09:36.535109	1000.00	123470	SAIDA	123466	123470
12	2025-12-21 23:13:52.03528	3000.00	123466	SAIDA	123470	123466
13	2025-12-21 23:17:36.8813	852.00	123466	SAIDA	123471	123466
14	2025-12-21 23:20:36.422079	123.00	123472	SAIDA	123470	123472
15	2025-12-21 23:20:48.476491	234.00	123472	SAIDA	123471	123472
16	2025-12-21 23:21:04.007886	345.00	123472	SAIDA	123466	123472
17	2025-12-21 23:25:02.746766	123.00	123472	SAIDA	123	123472
18	2025-12-21 23:32:09.08893	123.00	123472	SAIDA	123471	123472
19	2025-12-21 23:49:47.817849	123451111.11	123472	SAIDA	123456	123472
20	2025-12-21 23:50:03.968437	999999999.99	123472	SAIDA	123456	123472
21	2025-12-22 18:44:13.87103	25.14	123471	SAIDA	123470	123471
22	2025-12-22 19:29:07.703809	2300.00	123471	SAIDA	123466	123471
23	2025-12-22 21:21:32.560335	2563.65	123470	SAIDA	123471	123470
\.


--
-- Data for Name: posts; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.posts (id, title, content, created_at) FROM stdin;
\.


--
-- Name: clientes_id_cliente_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.clientes_id_cliente_seq', 1, true);


--
-- Name: contas_numeroconta_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.contas_numeroconta_seq', 123473, true);


--
-- Name: livrocaixa_idtransacao_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.livrocaixa_idtransacao_seq', 23, true);


--
-- Name: posts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.posts_id_seq', 1, false);


--
-- Name: clientes clientes_cpf_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clientes
    ADD CONSTRAINT clientes_cpf_key UNIQUE (cpf);


--
-- Name: clientes clientes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clientes
    ADD CONSTRAINT clientes_pkey PRIMARY KEY (id_cliente);


--
-- Name: contas contas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contas
    ADD CONSTRAINT contas_pkey PRIMARY KEY (numeroconta);


--
-- Name: livrocaixa livrocaixa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.livrocaixa
    ADD CONSTRAINT livrocaixa_pkey PRIMARY KEY (idtransacao);


--
-- Name: posts posts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.posts
    ADD CONSTRAINT posts_pkey PRIMARY KEY (id);


--
-- Name: idx_clientes_numeroconta; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX idx_clientes_numeroconta ON public.clientes USING btree (numeroconta);


--
-- Name: livrocaixa fk_conta; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.livrocaixa
    ADD CONSTRAINT fk_conta FOREIGN KEY (numeroconta) REFERENCES public.contas(numeroconta);


--
-- Name: clientes fk_conta_cliente; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clientes
    ADD CONSTRAINT fk_conta_cliente FOREIGN KEY (numeroconta) REFERENCES public.contas(numeroconta) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--

\unrestrict aj3uDA4rM6cEvEBRiwBWIIMpD6269yZpXZRYYCbkOPoXUWSpaR7caBvPF8JSIAN

