package com.dimafeng.testcontainers

import com.dimafeng.testcontainers.MySQLContainer.DEFAULT_MYSQL_VERSION
import org.testcontainers.containers.{MySQLContainer => OTCMySQLContainer}


class MySQLContainer(configurationOverride: Option[String] = None,
                     mysqlImageVersion: Option[String] = None,
                     databaseName: Option[String] = None,
                     mysqlUsername: Option[String] = None,
                     mysqlPassword: Option[String] = None)
  extends SingleContainer[OTCMySQLContainer[_]] {

  override val container: OTCMySQLContainer[_] = mysqlImageVersion
    .map(new OTCMySQLContainer(_))
    .getOrElse(new OTCMySQLContainer(DEFAULT_MYSQL_VERSION))

  databaseName.map(container.withDatabaseName)
  mysqlUsername.map(container.withUsername)
  mysqlPassword.map(container.withPassword)

  configurationOverride.foreach(container.withConfigurationOverride)

  def driverClassName: String = container.getDriverClassName

  def jdbcUrl: String = container.getJdbcUrl

  def password: String = container.getPassword

  def testQueryString: String = container.getTestQueryString

  def username: String = container.getUsername

}

object MySQLContainer {
  val DEFAULT_MYSQL_VERSION = "mysql:5"

  def apply(configurationOverride: String = null,
            mysqlImageVersion: String = null,
            databaseName: String = null,
            username: String = null,
            password: String = null): MySQLContainer =
    new MySQLContainer(Option(configurationOverride),
      Option(mysqlImageVersion),
      Option(databaseName),
      Option(username),
      Option(password))

}
