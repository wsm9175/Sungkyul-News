'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class comment extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
    }
  };
  comment.init({
    post_number: DataTypes.INTEGER,
    real_comment: DataTypes.STRING,
    user_id: DataTypes.STRING,
    user_name: DataTypes.STRING
  }, {
    sequelize,
    modelName: 'comment',
  });
  return comment;
};