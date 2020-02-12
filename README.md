# Slack random pick

On-duty application linked with Slack.

# Run

```
$ sbt run
```

# Endpoint

- http://localhost:9000/random-pick
  - slackApiToken: 設定したSlackAPIトークン
  - pickUpNum: ピックアップしたい人数
  - channelId: ピックアップ対象のチャンネルID

# Response

Success case

```
{
    "ok" :      true,
    "profile" : {...},
    ...
}
```

Error case

```
{
    "ok" :      false, 
    "error" :   ...
}
```

# References

- [WEB+DB PRESS Vol.112](https://gihyo.jp/magazine/wdpress/archive/2019/vol112)
- [slack api Your Apps](https://api.slack.com/apps)
- [OpenJDK](https://openjdk.java.net/)
- [The Scala Programming Language](https://www.scala-lang.org/)
- [sbt](https://www.scala-sbt.org/index.html)
- [Visual Studio Code](https://code.visualstudio.com/)
- [Developing inside a Container](https://code.visualstudio.com/docs/remote/containers)
- [Metals](https://scalameta.org/metals/)