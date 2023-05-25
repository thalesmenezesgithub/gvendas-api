CREATE TABLE produto (
	id BIGSERIAL PRIMARY KEY NOT NULL,
	descricao VARCHAR(100) NOT NULL,
	quantidade INTEGER NOT NULL,
	preco_custo DECIMAL(10,2) NOT NULL,
	preco_venda DECIMAL(10,2) NOT NULL,
	observacao VARCHAR(500),
	id_categoria BIGINT NOT NULL,
	FOREIGN KEY (id_categoria) REFERENCES categoria(id)
) 