# Database_Assignment
## develop environment
### dependencies
<dependencies>
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>fastjson</artifactId>
        <version>1.2.68</version>
    </dependency><br>
    <dependency>
        <groupId>javax.persistence</groupId>
        <artifactId>javax.persistence-api</artifactId>
        <version>2.2</version>
    </dependency><br>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
        <version>2.2.6.RELEASE</version>
    </dependency>
</dependencies>

## interfaces
### DAO interface（待定）
json:<br>
> {<br>
>> **"type"**: "insert/delete/update/select",<br>
> **"tables"**: \["tableName"...], `only one for insert/delete/update`<br>
> **"attributes"**: \[{<br>
>>> "attributeName": "name",<br>
> "aggregate": "COUNT/SUM/MAX/MIN/AVE   ", `only for select`<br>
> "value": "attValue"}, `only for insert/update` ...],<br>
>
>
>
>}