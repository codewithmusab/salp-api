--------------------------------------------------------------------------------------------------------------------------------
-- SAJP.DOCUMENTOS
--------------------------------------------------------------------------------------------------------------------------------
CREATE TABLE SAJP.DOCUMENTOS
(
  ID NUMBER(10) NOT NULL
, NOMBRE VARCHAR2(128) NOT NULL
, TIPO_ID NUMBER(10) NOT NULL
, CLIENTE_ID NUMBER(10)
, URL_ADJUNTO VARCHAR2(200) NOT NULL
, NOMBRE_ADJUNTO VARCHAR2(150) NOT NULL
, CONTENT_TYPE_ADJUNTO VARCHAR2(50) NOT NULL
, FECHA DATE NOT NULL
, VISIBLE_EXTRANET NUMBER(1) DEFAULT 1 NOT NULL
, VISIBLE_HASTA DATE
, TERRITORIO_COMPLETO NUMBER(1)
, SECTORES_COMPLETO NUMBER(1)
, DESCRIPCION VARCHAR2(512)
, OBSERVACIONES_INTERNAS VARCHAR2(256)
, CREADO DATE NOT NULL
, CREADO_POR NUMBER(10) NOT NULL
, MODIFICADO DATE
, MODIFICADO_POR NUMBER(10)
, BORRADO DATE
, BORRADO_POR NUMBER(10)
, CONSTRAINT TABLE1_PK PRIMARY KEY
  (
    ID
  )
  ENABLE
);

COMMENT ON COLUMN DOCUMENTOS.ID IS 'Primary key de la tabla';
COMMENT ON COLUMN DOCUMENTOS.NOMBRE IS 'Nombre del documento';
COMMENT ON COLUMN DOCUMENTOS.TIPO_ID IS 'FK: SAJP.TM_DOCUMENTOS_TIPOS.ID';
COMMENT ON COLUMN DOCUMENTOS.CLIENTE_ID IS 'Cliente al que se asocia el documento en caso de ser un documento privado.
FK: SAJP.GC2006_RELEASE.PC_CLIENTES.ID';

COMMENT ON COLUMN DOCUMENTOS.URL_ADJUNTO IS 'URL del fichero adjunto al documento';
COMMENT ON COLUMN DOCUMENTOS.NOMBRE_ADJUNTO IS 'Nombre del fichero adjunto al documento';
COMMENT ON COLUMN DOCUMENTOS.CONTENT_TYPE_ADJUNTO IS 'Content-Type del fichero adjunto al documento';
COMMENT ON COLUMN DOCUMENTOS.FECHA IS 'Fecha desde la que el cliente podrá ver el documento en la extranet';
COMMENT ON COLUMN DOCUMENTOS.VISIBLE_EXTRANET IS '0 - No visible, 1 - Visible';
COMMENT ON COLUMN DOCUMENTOS.VISIBLE_HASTA IS 'Fecha hasta la que el cliente podrá visualizar el documento en la extranet';
COMMENT ON COLUMN DOCUMENTOS.TERRITORIO_COMPLETO IS '0 - Se aplica a comunidades concretas
Tendrá registros en SAJP.DOCUMENTOS_X_COMUNIDADES.ID
1 - Se aplica a todo el territorio nacional
NULL - Para documentos privados de clientes
';

COMMENT ON COLUMN DOCUMENTOS.SECTORES_COMPLETO IS '0 - Se aplica a sectores concretos
Tendrá registros en SAJP.DOCUMENTOS_X_SECTORES.ID
1 - Se aplica a todos los sectores
NULL - Para documentos privados de clientes
';

COMMENT ON COLUMN DOCUMENTOS.DESCRIPCION IS 'Descripción visible para el cliente.
Será obligatorio cuando sea visible en la extranet de clientes.';

COMMENT ON COLUMN DOCUMENTOS.OBSERVACIONES_INTERNAS IS 'Observaciones no visibles para el cliente';
COMMENT ON COLUMN DOCUMENTOS.CREADO IS 'Fecha de creación del registro';
COMMENT ON COLUMN DOCUMENTOS.CREADO_POR IS 'Usuario que realiza la creación del registro.
FK: GC2006_RELEASE.PC_USUARIOS.ID';

COMMENT ON COLUMN DOCUMENTOS.MODIFICADO IS 'Fecha de la última modificación del registro';
COMMENT ON COLUMN DOCUMENTOS.MODIFICADO_POR IS 'Usuario que realiza la última modificación del registro.
FK: GC2006_RELEASE.USUARIOS.ID';

COMMENT ON COLUMN DOCUMENTOS.BORRADO IS 'Fecha del borrado del registro';
COMMENT ON COLUMN DOCUMENTOS.BORRADO_POR IS 'Usuario que realiza el borrado del registro.
FK: GC2006_RELEASE.USUARIOS.ID';

--------------------------------------------------------------------------------------------------------------------------------
-- RESTRICCIONES
--------------------------------------------------------------------------------------------------------------------------------
ALTER TABLE SAJP.DOCUMENTOS
ADD CONSTRAINT DOCUMENTOS_TIPO_ID FOREIGN KEY (TIPO_ID)
REFERENCES SAJP.TM_DOCUMENTOS_TIPOS (ID)
ENABLE;

ALTER TABLE SAJP.DOCUMENTOS
ADD CONSTRAINT DOCUMENTOS_CLIENTE_ID FOREIGN KEY (CLIENTE_ID)
REFERENCES GC2006_RELEASE.PC_CLIENTES (ID)
ENABLE;

ALTER TABLE SAJP.DOCUMENTOS
ADD CONSTRAINT DOCUMENTOS_CREADO_POR FOREIGN KEY (CREADO_POR)
REFERENCES GC2006_RELEASE.PC_USUARIOS (ID)
ENABLE;

ALTER TABLE SAJP.DOCUMENTOS
ADD CONSTRAINT DOCUMENTOS_MODIFICADO_POR FOREIGN KEY (MODIFICADO_POR)
REFERENCES GC2006_RELEASE.PC_USUARIOS (ID)
ENABLE;

ALTER TABLE SAJP.DOCUMENTOS
ADD CONSTRAINT DOCUMENTOS_BORRADO_POR FOREIGN KEY (BORRADO_POR)
REFERENCES GC2006_RELEASE.PC_USUARIOS (ID)
ENABLE;

--------------------------------------------------------------------------------------------------------------------------------
-- SAJP.TM_DOCUMENTOS_TIPOS
--------------------------------------------------------------------------------------------------------------------------------
CREATE TABLE SAJP.TM_DOCUMENTOS_TIPOS
(
  ID NUMBER(10) NOT NULL
, DENOMINACION VARCHAR2(150) NOT NULL
, ACTIVO NUMBER(1) DEFAULT 1 NOT NULL
, DESCRIPCION VARCHAR2(500)
, CONSTRAINT TM_DOCUMENTOS_TIPOS_PK PRIMARY KEY
  (
    ID
  )
  ENABLE
);

COMMENT ON COLUMN TM_DOCUMENTOS_TIPOS.ID IS 'Primary key de la tabla';
COMMENT ON COLUMN TM_DOCUMENTOS_TIPOS.DENOMINACION IS 'Denominación del tipo de documento';
COMMENT ON COLUMN TM_DOCUMENTOS_TIPOS.ACTIVO IS 'Identifica si el documento se encuentra activo.';
COMMENT ON COLUMN TM_DOCUMENTOS_TIPOS.DESCRIPCION IS 'Descripción referente al documento';

--------------------------------------------------------------------------------------------------------------------------------
-- SAJP.ENVIOS_MAIL
--------------------------------------------------------------------------------------------------------------------------------
CREATE TABLE SAJP.ENVIOS_MAIL
(
  ID NUMBER(10) NOT NULL
, TIPO_NOTIFICACION NUMBER(1) NOT NULL
, ENVIADO TIMESTAMP
, CONSTRAINT ENVIOS_MAIL_PK PRIMARY KEY
  (
    ID
  )
  ENABLE
);

COMMENT ON COLUMN ENVIOS_MAIL.ID IS 'Primary key de la tabla';
COMMENT ON COLUMN ENVIOS_MAIL.TIPO_NOTIFICACION IS '1: Inmediata, 2: En cola';
COMMENT ON COLUMN ENVIOS_MAIL.ENVIADO IS 'Fecha en la que se realiza el envío del mail';

--------------------------------------------------------------------------------------------------------------------------------
-- SAJP.ENVIOS_X_DOCUMENTOS
--------------------------------------------------------------------------------------------------------------------------------
CREATE TABLE SAJP.ENVIOS_X_DOCUMENTOS
(
  ID NUMBER(10) NOT NULL
, ENVIO_ID NUMBER(10) NOT NULL
, DOCUMENTO_ID NUMBER(10) NOT NULL
, CONSTRAINT ENVIOS_X_DOCUMENTOS_PK PRIMARY KEY
  (
    ID
  )
  ENABLE
);

COMMENT ON COLUMN ENVIOS_X_DOCUMENTOS.ID IS 'Primary key de la tabla';
COMMENT ON COLUMN ENVIOS_X_DOCUMENTOS.ENVIO_ID IS 'FK: SAJP.ENVIOS_MAIL.ID';
COMMENT ON COLUMN ENVIOS_X_DOCUMENTOS.DOCUMENTO_ID IS 'FK: SAJP.DOCUMENTOS.ID';

--------------------------------------------------------------------------------------------------------------------------------
-- RESTRICCIONES
--------------------------------------------------------------------------------------------------------------------------------

ALTER TABLE SAJP.ENVIOS_X_DOCUMENTOS
ADD CONSTRAINT ENVIOS_MAIL_ID FOREIGN KEY (ENVIO_ID)
REFERENCES SAJP.ENVIOS_MAIL (ID)
ENABLE;

ALTER TABLE SAJP.ENVIOS_X_DOCUMENTOS
ADD CONSTRAINT DOCUMENTOS_ID FOREIGN KEY (DOCUMENTO_ID)
REFERENCES SAJP.DOCUMENTOS (ID)
ENABLE;

-------------------------------------------------------------------------------------------------------------------------
-- PERMISOS
-----------
GRANT REFERENCES ON GC2006_RELEASE.PC_CLIENTES TO SAJP;
GRANT REFERENCES ON GC2006_RELEASE.PC_USUARIOS TO SAJP;
GRANT REFERENCES ON GC2006_RELEASE.PC_CLIENTES TO SAJP;
GRANT SELECT ON GC2006_RELEASE.PC_CLIENTES TO SAJP;
GRANT SELECT ON GC2006_RELEASE.PC_USUARIOS TO SAJP;

GRANT SELECT ON GC2006_RELEASE.PC_USUARIOSGRUPOS TO SAJP;
GRANT SELECT ON GC2006_RELEASE.PC_CNAES TO SAJP;
GRANT SELECT ON GC2006_RELEASE.PC_COLABORADOR TO SAJP;
GRANT SELECT ON GC2006_RELEASE.PC_GRUPOS_CLIENTES TO SAJP;
GRANT SELECT ON VIG_SALUD.LOCALIDADES TO SAJP;
GRANT SELECT ON VIG_SALUD.PROVINCIAS TO SAJP;
GRANT SELECT ON GC2006_RELEASE.TM_ENTIDADES TO SAJP;

GRANT SELECT, INSERT, UPDATE, DELETE ON CRM.CONTACTOS TO SAJP;
GRANT SELECT ON CRM.CONTACTOS_SEQ TO SAJP;
GRANT SELECT ON GC2006_RELEASE.NMC_OFERTAS TO SAJP;
GRANT SELECT ON GC2006_RELEASE.NMC_OA TO SAJP;
GRANT SELECT ON GC2006_RELEASE.NMC_OA_X_SERVICIOS TO SAJP;
GRANT SELECT ON GC2006_RELEASE.NMC_OAH_X_SERVICIOS TO SAJP;
GRANT SELECT ON GC2006_RELEASE.NMC_SERVICIOS TO SAJP;

-------
-- SECUENCIAS
--------------
CREATE SEQUENCE DOCUMENTOS_SEQ INCREMENT BY 1 START WITH 1 MAXVALUE 9999999999999999999999999999999 MINVALUE 1;
