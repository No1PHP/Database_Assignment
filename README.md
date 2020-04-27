# Database_Assignment
## develop environment
### dependencies
<dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13</version>
            <scope>compile</scope>
        </dependency><br>
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-entitymanager</artifactId>
            <version>5.4.14.Final</version>
        </dependency><br>
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-c3p0</artifactId>
            <version>5.4.14.Final</version>
        </dependency><br>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.19</version>
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