# Almost Secure Storage CLI
Command Line Interface implementation of AlmostSecureStorage

## Usage
### Create database
```shell
java -jar artifact.jar storage.ass create
```

### Get basic information
```shell
java -jar artifact.jar storage.ass db-info
```

### Get list of data entries in storage
```shell
java -jar artifact.jar storage.ass ls
```

### Get data entry from storage
```shell
java -jar artifact.jar storage.ass get-entry entryName
```

### Add new data entry into storage
```shell
java -jar artifact.jar storage.ass add-entry
```

### Delete data entry from storage
```shell
java -jar artifact.jar storage.ass del-entry entryName
```

### Add/update property of entry in storage
```shell
java -jar artifact.jar storage.ass set-prop entryName propName newPropValue
```

### Delete property of data entry
```shell
java -jar artifact.jar storage.ass del-prop entryName propName
```