/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.documento.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import lombok.Setter;
import mx.gob.imss.dpes.baseback.persistence.BaseSpecification;
import mx.gob.imss.dpes.baseback.service.BasePageableCRUDService;
import mx.gob.imss.dpes.common.enums.TipoDocumentoEnum;
import mx.gob.imss.dpes.common.exception.AlternateFlowException;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.PageRequestModel;
import mx.gob.imss.dpes.documento.entity.McltDocumento;
import mx.gob.imss.dpes.documento.model.CepCompraReq;
import mx.gob.imss.dpes.documento.repository.DocumentoByEntidadSpecification;
import mx.gob.imss.dpes.documento.repository.DocumentoByFechaAltaSpecification;
import mx.gob.imss.dpes.documento.repository.DocumentoByRefBovedaNotNullSpecification;
import mx.gob.imss.dpes.documento.repository.DocumentoByTipoSpecification;
import mx.gob.imss.dpes.documento.repository.DocumentoRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author juanf.barragan
 */
@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class ObtenerCepCompraService extends BasePageableCRUDService<CepCompraReq, McltDocumento, Long, Long>{
    
    @Autowired
    @Setter
    private DocumentoRepository repository;
    
    
    @Override
    public Collection<BaseSpecification<McltDocumento>> customConstraints (CepCompraReq model){
        Collection<BaseSpecification<McltDocumento>> constraints = new ArrayList<>();
        log.log(Level.INFO, ">>> documentoBack|ObtenerCepCompraService|customConstraints {0}", model);

            constraints.add(new DocumentoByFechaAltaSpecification(model.getInicio(), model.getFin()));   
            constraints.add(new DocumentoByTipoSpecification(TipoDocumentoEnum.CEP_ENTIDAD_FINANCIERA));
            constraints.add(new DocumentoByEntidadSpecification(model.getEntFinanciera()));
            constraints.add(new DocumentoByRefBovedaNotNullSpecification());
        log.log(Level.INFO, ">>> documentoBack|ObtenerCepCompraService|customConstraints {0}", constraints);
         return constraints;
    }
    
    
    public Page<McltDocumento> obtenerCepCompras (PageRequestModel<CepCompraReq> request)throws BusinessException{
        log.log(Level.INFO, ">>> ObtenerCepCompraService.obtenerCepCompras SERVICE 1 {0}", request);
        List<Sort.Order> listaDocumentos;
        Page<McltDocumento> page = null;
        log.log(Level.INFO, ">>> ObtenerCepCompraService.obtenerCepCompras SERVICE 2 {0}", request.getPage());
        if (request.getPage() > 0) {
            listaDocumentos = new ArrayList<>();
            listaDocumentos.add(new Sort.Order(Sort.Direction.ASC, "altaRegistro"));
            log.log(Level.INFO, ">>> ObtenerCepCompraService.obtenerCepCompras 3 {0}", request);
            log.log(Level.INFO, ">>> ObtenerCepCompraService.obtenerCepCompras 4 {0}", listaDocumentos);
            
            page = fetch(request, listaDocumentos);
             
            log.log(Level.INFO, ">>> ObtenerCepCompraService.obtenerCepCompras 5 {0}", page); 
            
            return page;
        }
        throw new AlternateFlowException();
    }

    @Override
    public JpaSpecificationExecutor<McltDocumento> getRepository() {
        return repository;
    }

    @Override
    public JpaRepository<McltDocumento, Long> getJpaRepository() {
        return repository;
    }
    
}
