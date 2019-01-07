# Binary Diff

Simple web application for comparing JSON base64 encoded binary data.

## Starting the application

1) Clone this repository.

```sh
git clone git@github.com:alemasseroli/binary-diff.git
```

2) Enter project's directory.

```sh
cd binary-diff
```

3) Start web application running jar file.

```sh
java -jar binary-diff.jar
```

## Usage

- Setting \<ID>'s left member for comparison.

```sh
curl -XPUT localhost:8080/v1/diff/<ID>/left -d <DATA>
```

- Setting \<ID>'s right member for comparison.

```sh
curl -XPUT localhost:8080/v1/diff/<ID>/right -d <DATA>
```

- Getting \<ID>'s diff for set left and right members.

```sh
curl localhost:8080/v1/diff/<ID>
```

- Getting \<ID>'s left member.

```sh
curl localhost:8080/v1/diff/<ID>/left
```

- Getting \<ID>'s right member for comparison.

```sh
curl localhost:8080/v1/diff/<ID>/right
```

### Example

```sh
curl -XPUT localhost:8080/v1/diff/id1/left -d 'VGVzdGluZyBkYXRh'
```
> {"status":200, "message":"ok", "value":"Testing data"}

```sh
curl -XPUT localhost:8080/v1/diff/id1/right -d 'VGVzdGluZyBkYXRh'
```
> {"status":200, "message":"ok", "value":"Testing data"}


```sh
curl localhost:8080/v1/diff/id1
```
> {"status":200, "message":"ok", "value":"Values are equal!"}

