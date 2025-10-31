/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.documento.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.entity.LogicDeletedEntity;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author andrea.cervantes
 */
@Entity
@Table(name = "MCLT_DOCUMENTO")
public class McltDocumento extends LogicDeletedEntity<Long> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CVE_DOCUMENTO")
    @Getter
    @Setter
    @GeneratedValue(generator = "SEQ_GEN_MCLS_DOCUMENTO")
    @GenericGenerator(
            name = "SEQ_GEN_MCLS_DOCUMENTO",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                @Parameter(name = "sequence_name", value = "MCLS_DOCUMENTO"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CVE_SOLICITUD")
    @Getter
    @Setter
    private Long cveSolicitud;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CVE_TIPO_DOCUMENTO")
    @Getter
    @Setter
    private Long tipoDocumento;

    @Column(name = "CVE_PERSONA")
    @Getter
    @Setter
    private Long cvePersona;

    @Column(name = "CVE_ENTIDAD_FINANCIERA")
    @Getter
    @Setter
    private Long cveEntidadFinanciera;

    @Column(name = "CVE_PRESTAMO_RECUPERACION")
    @Getter
    @Setter
    private Long cvePrestamoRecuperacion;

    @Size(max = 1500)
    @Column(name = "REF_SELLO")
    @Getter
    @Setter
    private String refSello;
    @Size(max = 4000)
    @Column(name = "REF_DOCUMENTO")
    @Getter
    @Setter
    private String refDocumento;

    @Lob
    @Column(name = "REF_DOCUMENTO_CLOB")
    @Getter
    @Setter
    private String refDocumentoClob;

    @Size(max = 100)
    @Column(name = "REF_DOC_BOVEDA")
    @Getter
    @Setter
    private String refDocBoveda;
    
    @Column(name = "IND_DOCUMENTO_HISTORICO")
    @Getter
    @Setter
    private boolean indDocumentoHistorico;
    
    @Column(name = "CVE_ENTIDAD_FINANCIERA_SIPRE")
    @Getter
    @Setter
    private String eFinanciera;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof McltDocumento)) {
            return false;
        }
        McltDocumento other = (McltDocumento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
