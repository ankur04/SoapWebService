/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.soapwebservice.models;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ankur
 */
@Entity
@Table(name = "TVSHOWS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tvshows.findAll", query = "SELECT t FROM Tvshows t"),
    @NamedQuery(name = "Tvshows.findById", query = "SELECT t FROM Tvshows t WHERE t.id = :id"),
    @NamedQuery(name = "Tvshows.findByName", query = "SELECT t FROM Tvshows t WHERE t.name = :name"),
    @NamedQuery(name = "Tvshows.findByType", query = "SELECT t FROM Tvshows t WHERE t.type = :type")})
public class Tvshows implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "NAME")
    private String name;
    @Size(max = 255)
    @Column(name = "TYPE")
    private String type;
    @Lob
    @Column(name = "IMAGE")
    private byte[] image;

    public Tvshows() {
    }

    public Tvshows(BigDecimal id) {
        this.id = id;
    }

    public Tvshows(BigDecimal id, String name) {
        this.id = id;
        this.name = name;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tvshows)) {
            return false;
        }
        Tvshows other = (Tvshows) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.soapwebservice.Tvshows[ id=" + id + " ]";
    }
    
}
