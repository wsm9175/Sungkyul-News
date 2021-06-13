'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class User extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
    }
  };
  User.init({
    user_id: DataTypes.STRING,
    user_password: DataTypes.STRING,
    user_name: DataTypes.STRING,
    user_major: DataTypes.STRING,
    user_email: DataTypes.STRING,
    user_phoneNumber: DataTypes.STRING,
    user_schoolId: DataTypes.INTEGER,
    user_exp: DataTypes.INTEGER,
  }, {
    sequelize,
    modelName: 'User',
  });
  return User;
};