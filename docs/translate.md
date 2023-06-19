## translate Ayah:Surah Quran API Spec

Endpoint : GET /api/{verse}

Request Header :

- X-API-TOKEN : Token (Mandatory)
- X-USER-ID : email (Mandatory)

Response Body (Success) :
```json
{
  "code": 200,
  "status": "OK",
  "data": {
    "number": 10,
    "text": "(yaitu) mereka yang beriman kepada yang ghaib, yang mendirikan shalat, dan menafkahkan sebahagian rezeki yang Kami anugerahkan kepada mereka.",
    "edition": {
      "identifier": "id.indonesian",
      "language": "id",
      "name": "Bahasa Indonesia",
      "englishName": "Unknown",
      "format": "text",
      "type": "translation",
      "direction": "ltr"
    },
    "surah": {
      "number": 2,
      "name": "سُورَةُ البَقَرَةِ",
      "englishName": "Al-Baqara",
      "englishNameTranslation": "The Cow",
      "numberOfAyahs": 286,
      "revelationType": "Medinan"
    },
    "numberInSurah": 3,
    "juz": 1,
    "manzil": 1,
    "page": 2,
    "ruku": 2,
    "hizbQuarter": 1,
    "sajda": false
  }
}
```
