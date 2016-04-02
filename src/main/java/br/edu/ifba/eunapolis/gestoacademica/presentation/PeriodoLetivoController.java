package br.edu.ifba.eunapolis.gestoacademica.presentation;

import br.edu.ifba.eunapolis.gestoacademica.model.PeriodoLetivo;
import br.edu.ifba.eunapolis.gestoacademica.presentation.util.JsfUtil;
import br.edu.ifba.eunapolis.gestoacademica.presentation.util.JsfUtil.PersistAction;
import br.edu.ifba.eunapolis.gestoacademica.session.PeriodoLetivoFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("periodoLetivoController")
@SessionScoped
public class PeriodoLetivoController implements Serializable {

    @EJB
    private br.edu.ifba.eunapolis.gestoacademica.session.PeriodoLetivoFacade ejbFacade;
    private List<PeriodoLetivo> items = null;
    private PeriodoLetivo selected;

    public PeriodoLetivoController() {
    }

    public PeriodoLetivo getSelected() {
        return selected;
    }

    public void setSelected(PeriodoLetivo selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PeriodoLetivoFacade getFacade() {
        return ejbFacade;
    }

    public PeriodoLetivo prepareCreate() {
        selected = new PeriodoLetivo();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PeriodoLetivoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PeriodoLetivoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PeriodoLetivoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<PeriodoLetivo> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public PeriodoLetivo getPeriodoLetivo(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<PeriodoLetivo> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PeriodoLetivo> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = PeriodoLetivo.class)
    public static class PeriodoLetivoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PeriodoLetivoController controller = (PeriodoLetivoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "periodoLetivoController");
            return controller.getPeriodoLetivo(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof PeriodoLetivo) {
                PeriodoLetivo o = (PeriodoLetivo) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), PeriodoLetivo.class.getName()});
                return null;
            }
        }

    }

}
