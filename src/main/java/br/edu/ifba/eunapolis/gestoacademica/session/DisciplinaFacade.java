/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifba.eunapolis.gestoacademica.session;

import br.edu.ifba.eunapolis.gestoacademica.model.Disciplina;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author root
 */
@Stateless
public class DisciplinaFacade extends AbstractFacade<Disciplina> {

    @PersistenceContext
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DisciplinaFacade() {
        super(Disciplina.class);
    }
    
}
