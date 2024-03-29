package com.project.webstore.domains;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Pedidos implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date instante;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido")
    private Pagamento pagamento;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "endereco_entrega_id")
    private Endereco enderecoEntrega;
    
    @OneToMany(mappedBy = "id.pedido")
    private Set<ItemPedido> itens = new HashSet<>();

    public Pedidos() {
    }

    public Pedidos(Integer id, Date instante, Cliente cliente, Endereco enderecoEntrega) {
        this.id = id;
        this.instante = instante;
        this.cliente = cliente;
        this.enderecoEntrega = enderecoEntrega;
    }
    
    public double getValorTotal(){
        double  soma =0.0;
        for (ItemPedido ip : itens){
            soma = soma + ip.getSubtotal();
        }
        return soma;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getInstante() {
        return instante;
    }

    public void setInstante(Date instante) {
        this.instante = instante;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(Endereco enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public Set<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(Set<ItemPedido> itens) {
        this.itens = itens;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pedidos other = (Pedidos) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {

        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt, BR"));
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, hh:mm:ss");
        
        StringBuilder builder = new StringBuilder();

        builder.append("Pedido Nº ");
        builder.append(getId());
        builder.append(", Instante: ");
        builder.append(sdf.format(getInstante()));
        builder.append(", Cliente: ");
        builder.append(getCliente());
        builder.append(", Situação Pgto.: ");
        builder.append(getPagamento().getEstado().getDescricao());
        builder.append("\nDetalhes: ");

        for (ItemPedido ip : getItens()) {

            builder.append(ip.toString());

        }
        builder.append("Valor total: ");
        builder.append(nf.format(getValorTotal()));

        return builder.toString();

    }

}
