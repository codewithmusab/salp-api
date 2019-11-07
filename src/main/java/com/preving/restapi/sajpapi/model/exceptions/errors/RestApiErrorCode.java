package com.preving.restapi.sajpapi.model.exceptions.errors;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RestApiErrorCode {

    USER_INCORRECT(1001, "Revise los datos del usuario. Validación de datos incorrecta"),

    USER_NOT_FOUND(1002, "No se encuentra el usuario en el sistema"),


    // Definir nuevos codigos de error aqui
    MARCAR_EMISION_INICIADA(2001, "Error al marcar la emision como iniciada"),

    MARCAR_EMISION_GENERADA(2002, "Error al marcar la emision como generada"),

    MARCAR_COMUNICADO_INTERNO(2003, "Error al marcar comunicado interno"),

    CANCELAR_EMISION(2004, "Error al cancelar la emisión"),

    ELIMINAR_EMISION(2005, "Error al eliminar la emisión"),

    DESCARGAR_EPID(2006, "Error al descargar el informe de la emisión"),

    FILE_ODT_MEDICO(2007, "Archivo seleccionado no válido. Extensiones permitidas .docx y .odt"),

    UPDATE_ODT_MEDICO(2008, "Error al actualizar la ruta del fichero ODT o DOCX de la emisión"),

    UPDATE_PDF(2009, "Error al actualizar la ruta del fichero PDF de la emisión"),

    UPLOAD_ERROR(2010, "Error al seleccionar la ruta de subida del archivo seleccionado."),

    UPDATE_EPID_FIRMA(2011, "Error al modificar el registro con la firma de la emisión seleccionada."),

    ERROR_ENVIAR_EMAIL(2012, "Error al comunicar finalización de generación de Estudio Epidemiológico al usuario que la solicitó."),

    ERROR_GENERACION_INFORME(3001, "Error generación informe"),

    ERROR_CONVERSION_INFORME(3002, "Error al convertir el fichero a pdf"),

    ERROR_FIRMA_DIGITAL(3003, "Error al firmar digitalmente el archivo de conversión"),

    ERROR_INSERT_AMT(3004, "Error al crear las actividad/es"),

    // códigos >= 9000 para excepciones no controladas y otros errores
    EXCEPCION_NO_CONTROLADA(9000, "Excepción no controlada por el sistema"),

    ERROR_ACCESO_DATOS(9001, "Error de acceso a datos");

    private final int value;
    private final String message;

    RestApiErrorCode(int value, String message) {
        this.value = value;
        this.message = message;
    }

    /**
     * Return the integer value of this rest api code.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Return the message of this rest api code.
     */
    public String getMessage() {
        return this.message;
    }

    public String toString() {
        return Integer.toString(this.value);
    }
    /**
     * Return the enum constant of this type with the specified numeric value.
     * @param codeValue the numeric value of the enum to be returned
     * @return the enum constant with the specified numeric value
     * @throws IllegalArgumentException if this enum has no constant for the specified numeric value
     */
    public static RestApiErrorCode valueOf(int codeValue) {
        for (RestApiErrorCode code : values()) {
            if (code.value == codeValue) {
                return code;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + codeValue + "]");
    }



}
