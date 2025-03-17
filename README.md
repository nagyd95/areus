## 1. Bejelentkezés és JWT token megszerzése

A többi végpont eléréséhez először autentikálnod kell magad a `/login` végponton keresztül, ahol egy tetszőleges név megadásával JWT tokent kapsz válaszként.

### **Bejelentkezési kérés** (POST)

**URL:** `/login`

**Törzstest (Body):**
```json
{
    "username": "yourUsername"
}
```
### További végpontok
- **`/create`**: Új felhasználó létrehozása (POST)
- **`/getById/{id}`**: Felhasználó lekérdezése ID alapján (GET)
- **`/delete/{id}`**: Felhasználó törlése (DELETE)
- **`/update/{id}`**: Felhasználó frissítése (PUT)
- **`/averageAge`**: Átlagos életkor lekérdezése (GET)
