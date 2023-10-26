# API Spec

## Create Product
- Method : POST
- Endpoint : `api/product`
- Header : 
  - Content-Type : application/json
  - Accept : application/json
- Body : 
```json
 {
    "id": "string, unique",
    "name": "string",
    "description": "string",
    "price": "long",
    "quantity": "int"
 }
```

- Response:
```json
{
  "code": "string",
  "status": "int",
  "data": {
    "id": "string, unique",
    "name": "string",
    "description": "string",
    "price": "long",
    "quantity": "int",
    "created_at": "date",
    "updated_at": "date"
  }
}
```

## Get Product
- Method : GET
- Endpoint : `api/product/{product_id}`
- Header :
  - Accept : application/json
- Response:
```json
{
  "code": "string",
  "status": "int",
  "data": {
    "id": "string, unique",
    "name": "string",
    "description": "string",
    "price": "long",
    "quantity": "int",
    "created_at": "date",
    "updated_at": "date"
  }
}
```

## List Products
- Method : GET
- Endpoint : `api/product`
- Header :
  - Accept : application/json
- Response:
```json
{
  "code": "string",
  "status": "int",
  "data": [
    {
      "id": "string, unique",
      "name": "string",
      "description": "string",
      "price": "long",
      "quantity": "int",
      "created_at": "date",
      "updated_at": "date"
    }
  ]
}
```

## Update Product
- Method : PUT
- Endpoint : `api/product/{product_id}`
- Header :
  - Content-Type : application/json 
  - Accept : application/json
- Body:
```json
{
  "name": "string",
  "description": "string",
  "price": "long",
  "quantity": "int"
}
```

- Response:
```json
{
  "code": "string",
  "status": "int",
  "data": {
      "id": "string, unique",
      "name": "string",
      "description": "string",
      "price": "long",
      "quantity": "int",
      "created_at": "date",
      "updated_at": "date"
    }
}
```

## Delete Product
- Method : DELETE
- Endpoint : `api/product/{product_id}`
- Header :
  - Accept : application/json
- Response:
```json
{
  "code": "string",
  "status": "int"
}
```