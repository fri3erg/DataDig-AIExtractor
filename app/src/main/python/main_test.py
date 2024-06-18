import time


n=10 

def process_data(data, progress_callback):
  """
  Funzione che elabora i dati e invia messaggi di avanzamento a Chaquopy.

  Args:
    data: I dati da elaborare (stringa in questo esempio).
    progress_callback: Una funzione di callback da chiamare per aggiornare lo stato di avanzamento.
  """

  # Simulare l'elaborazione dei dati (invio di messaggi di avanzamento al 10%)
  for i in range(1, 11):
    progress_callback(i / 10)
    time.sleep(1)

  # Elaborazione effettiva dei dati (sostituisci con la tua logica)
  processed_data = data

  # Invia messaggio di avanzamento al 100%
  progress_callback(1.0)

  # Restituisci i dati elaborati
  return processed_data
