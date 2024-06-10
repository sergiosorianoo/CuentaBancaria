package com.politecnicomalaga.entidadbancaria;

import java.util.ArrayList;
import java.util.List;

public class Cuenta {

	private String ccc; // Código de la cuenta
	private float saldo;
	private String fechaApertura;
	private List<Operacion> operaciones;
	private Cliente clienteCuenta;

	public Cuenta(String ccc, float saldo, String fechaApertura) {
		this.ccc = ccc;
		this.saldo = saldo;
		this.fechaApertura = fechaApertura;
		this.operaciones = new ArrayList<>();
		this.clienteCuenta = null; // Inicialmente el cliente es null
	}

	public String getCcc() {
		return ccc;
	}

	public void setCcc(String ccc) {
		this.ccc = ccc;
	}

	public float getSaldo() {
		return saldo;
	}

	public String getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(String fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public boolean isActiva() {
		return saldo > 0;
	}

	public boolean retirarEfectivo(float cantidad, String fecha) {
		if (cantidad <= 0 || cantidad > saldo)
			return false;

		saldo -= cantidad;
		agregarOperacion(cantidad * -1, fecha);
		return true;
	}

	public boolean ingresarEfectivo(float cantidad, String fecha) {
		if (cantidad <= 0)
			return false;

		saldo += cantidad;
		agregarOperacion(cantidad, fecha);
		return true;
	}

	private void agregarOperacion(float cantidad, String fecha) {
		long codigo;
		do {
			codigo = Math.round(Math.random() * 1000000);
		} while (codigoRepetido(codigo));

		Operacion operacion = new Operacion(codigo, fecha, cantidad);
		operaciones.add(operacion);
	}

	private boolean codigoRepetido(long codigo) {
		for (Operacion operacion : operaciones) {
			if (operacion.getCodigo() == codigo)
				return true;
		}
		return false;
	}

	public List<Operacion> getOperaciones() {
		return operaciones;
	}

	public Cliente getClienteCuenta() {
		return clienteCuenta;
	}

	public void setClienteCuenta(Cliente clienteCuenta) {
		this.clienteCuenta = clienteCuenta;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Cuenta{ccc=").append(ccc);
		sb.append("; Saldo=").append(saldo);
		sb.append("; FechaApertura=").append(fechaApertura);
		sb.append("; ClienteCuenta=").append(clienteCuenta != null ? clienteCuenta.toString() : "null");
		sb.append("; Operaciones=").append(operacionesToString());
		sb.append('}');
		return sb.toString();
	}

	private String operacionesToString() {
		StringBuilder sb = new StringBuilder();
		for (Operacion operacion : operaciones) {
			sb.append(operacion.toString()).append("In");
		}
		return sb.toString();
	}
}
