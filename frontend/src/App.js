
import React, { useEffect, useState } from "react";

const API_URL = "http://localhost:8080/api/parkingspots";

function App() {
  // Estado para el informe de pagos de residentes
  const [residentReport, setResidentReport] = useState([]);
  const [showReport, setShowReport] = useState(false);

  // Obtener el informe de pagos de residentes
  const fetchResidentReport = async () => {
    try {
      const res = await fetch('http://localhost:8080/api/reports/resident-payments-table');
      if (res.ok) {
        const data = await res.json();
        setResidentReport(data);
      } else {
        setResidentReport([]);
      }
    } catch {
      setResidentReport([]);
    }
  };
  const [spots, setSpots] = useState([]);
  const [code, setCode] = useState("");
  const [error, setError] = useState("");

  const fetchSpots = async () => {
    const res = await fetch(API_URL);
    setSpots(await res.json());
  };

  useEffect(() => {
    fetchSpots();
  }, []);

  const handleAdd = async (e) => {
    e.preventDefault();
    setError("");
    if (!code) return;
    try {
      const res = await fetch(API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ code, occupied: false })
      });
      if (res.ok) {
        setCode("");
        fetchSpots();
      } else {
        const data = await res.text();
        setError(data || "No se pudo agregar la plaza.");
      }
    } catch (err) {
      setError("Error de red o servidor.");
    }
  };

  const toggleOccupied = async (id, current) => {
    setError("");
    const spot = spots.find(s => s.id === id);
    try {
      const res = await fetch(`${API_URL}/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ ...spot, occupied: !current })
      });
      if (!res.ok) {
        const data = await res.text();
        setError(data || "No se pudo actualizar el estado de la plaza.");
      } else {
        fetchSpots();
      }
    } catch (err) {
      setError("Error de red o servidor.");
    }
  };

  // NUEVO: Estados para los formularios de gestión
  const [plate, setPlate] = useState("");
  const [plateSalida, setPlateSalida] = useState("");
  const [plateOficial, setPlateOficial] = useState("");
  const [plateResidente, setPlateResidente] = useState("");
  const [filename, setFilename] = useState("");

  // Funciones para consumir los endpoints REST
  async function registrarEntrada(e) {
    e.preventDefault();
    setError("");
    if (!plate) return;
    try {
      // Validación client-side de placa
      if (!/^[A-Z]{3}[-][0-9]{4}$/.test(plate)) {
        setError(  <div>
          <strong>Formato de placa inválido. Ejemplo válido: ABC-1234</strong>
          <br />
          Explicación:
          <ul>
            <li>Tres letras mayúsculas (A-Z)</li>
            <li>Un guion medio (-)</li>
            <li>Cuatro números (0-9)</li>
          </ul>
        </div>);
        return;
      }
      const res = await fetch('http://localhost:8080/api/parking/entries', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ plate })
      });
      if (res.status === 201) {
        setPlate("");
        alert('Entrada registrada correctamente');
      } else {
        const data = await res.text();
        setError(data || 'No se pudo registrar la entrada.');
      }
    } catch (err) {
      setError("Error de red o servidor.");
    }
  }
  async function registrarSalida(e) {
    e.preventDefault();
    setError("");
    if (!plateSalida) return;
    try {
      const res = await fetch('http://localhost:8080/api/parking/salida?plate=' + plateSalida, { method: 'POST' });
      if (res.ok) {
        const pago = await res.json();
        alert('Importe a pagar: ' + pago);
        setPlateSalida("");
      } else {
        const data = await res.text();
        setError(data || 'No se pudo registrar la salida.');
      }
    } catch (err) {
      setError("Error de red o servidor.");
    }
  }
  async function altaOficial(e) {
    e.preventDefault();
    setError("");
    if (!plateOficial) return;
    try {
      const res = await fetch('http://localhost:8080/api/parking/alta-oficial?plate=' + plateOficial, { method: 'POST' });
      if (res.ok) {
        alert('Vehículo oficial dado de alta');
        setPlateOficial("");
      } else {
        const data = await res.text();
        setError(data || 'No se pudo dar de alta el vehículo oficial.');
      }
    } catch (err) {
      setError("Error de red o servidor.");
    }
  }
  async function altaResidente(e) {
    e.preventDefault();
    setError("");
    if (!plateResidente) return;
    try {
      const res = await fetch('http://localhost:8080/api/parking/alta-residente?plate=' + plateResidente, { method: 'POST' });
      if (res.ok) {
        alert('Vehículo residente dado de alta');
        setPlateResidente("");
      } else {
        const data = await res.text();
        setError(data || 'No se pudo dar de alta el vehículo residente.');
      }
    } catch (err) {
      setError("Error de red o servidor.");
    }
  }
  async function comienzaMes() {
    setError("");
    try {
      const res = await fetch('http://localhost:8080/api/parking/comienza-mes', { method: 'POST' });
      if (res.ok) {
        alert('Mes reiniciado');
      } else {
        const data = await res.text();
        setError(data || 'No se pudo reiniciar el mes.');
      }
    } catch (err) {
      setError("Error de red o servidor.");
    }
  }
  async function generarInforme(e) {
    e.preventDefault();
    setError("");
    if (!filename) return;
    try {
      const res = await fetch('http://localhost:8080/api/reports/resident-payments?filename=' + filename, { method: 'POST' });
      if (res.ok) {
        alert('Informe generado: ' + filename);
        setFilename("");
      } else {
        const data = await res.text();
        setError(data || 'No se pudo generar el informe.');
      }
    } catch (err) {
      setError("Error de red o servidor.");
    }
  }

  // Llamar a fetchResidentReport al montar el componente y tras generar informe
  useEffect(() => {
    fetchResidentReport();
  }, []);

  return (
      <div style={{ maxWidth: 900, margin: "2rem auto", fontFamily: "sans-serif" }}>
        <h2>Gestión de Parking</h2>
        {/* Formulario para registrar entrada */}
        <form onSubmit={registrarEntrada} style={{ marginBottom: 10 }}>
          <input value={plate} onChange={e => setPlate(e.target.value)} placeholder="Placa para entrada" required />
          <button type="submit">Registrar entrada</button>
        </form>
        {/* Formulario para registrar salida */}
        <form onSubmit={registrarSalida} style={{ marginBottom: 10 }}>
          <input value={plateSalida} onChange={e => setPlateSalida(e.target.value)} placeholder="Placa para salida" required />
          <button type="submit">Registrar salida</button>
        </form>
        {/* Alta oficial */}
        <form onSubmit={altaOficial} style={{ marginBottom: 10 }}>
          <input value={plateOficial} onChange={e => setPlateOficial(e.target.value)} placeholder="Placa oficial" required />
          <button type="submit">Alta oficial</button>
        </form>
        {/* Alta residente */}
        <form onSubmit={altaResidente} style={{ marginBottom: 10 }}>
          <input value={plateResidente} onChange={e => setPlateResidente(e.target.value)} placeholder="Placa residente" required />
          <button type="submit">Alta residente</button>
        </form>
        {/* Comienza mes */}
        <button onClick={comienzaMes} style={{ marginBottom: 20 }}>Comienza mes</button>
        {/* Generar informe pagos residentes */}
        <form onSubmit={async (e) => {
          await generarInforme(e);
          fetchResidentReport();
          setShowReport(true);
        }} style={{ marginBottom: 20 }}>
          <input value={filename} onChange={e => setFilename(e.target.value)} placeholder="Nombre archivo informe.csv" required />
          <button type="submit">Generar informe pagos residentes</button>
        </form>

        {/* Tabla de informe de pagos de residentes */}
        {showReport && (
            <>
              <h3>Informe de pagos de residentes</h3>
              <table border="1" cellPadding="8" style={{ width: "100%", marginBottom: 30 }}>
                <thead>
                <tr>
                  <th>Placa</th>
                  <th>Minutos estacionados</th>
                  <th>Importe a pagar (€)</th>
                </tr>
                </thead>
                <tbody>
                {residentReport.length === 0 ? (
                    <tr><td colSpan="3" style={{textAlign:'center'}}>No hay datos</td></tr>
                ) : (
                    residentReport.map((r, i) => (
                        <tr key={i}>
                          <td>{r.plate}</td>
                          <td>{r.minutes}</td>
                          <td>{r.amount.toFixed(2)}</td>
                        </tr>
                    ))
                )}
                </tbody>
              </table>
            </>
        )}
        {/* Gestión de plazas (lo que ya existía) */}
        <form onSubmit={handleAdd} style={{ marginBottom: 20 }}>
          <input
              value={code}
              onChange={e => setCode(e.target.value)}
              placeholder="Código de plaza"
              required
          />
          <button type="submit">Agregar plaza</button>
        </form>
        {error && <div style={{ color: "red" }}>{error}</div>}
        <table border="1" cellPadding="8" style={{ width: "100%" }}>
          <thead>
          <tr>
            <th>ID</th>
            <th>Código</th>
            <th>Ocupada</th>
            <th>Acción</th>
          </tr>
          </thead>
          <tbody>
          {spots.map(s => (
              <tr key={s.id}>
                <td>{s.id}</td>
                <td>{s.code}</td>
                <td>{s.occupied ? "Sí" : "No"}</td>
                <td>
                  <button onClick={() => toggleOccupied(s.id, s.occupied)}>
                    Marcar como {s.occupied ? "libre" : "ocupada"}
                  </button>
                </td>
              </tr>
          ))}
          </tbody>
        </table>
      </div>
  );
}

export default App;
