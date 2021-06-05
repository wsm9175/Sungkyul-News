'use strict';
const {
  Model
} = require('sequelize');
module.exports = (sequelize, DataTypes) => {
  class board extends Model {
    /**
     * Helper method for defining associations.
     * This method is not a part of Sequelize lifecycle.
     * The `models/index` file will call this method automatically.
     */
    static associate(models) {
      // define association here
    }
  };
  board.init({
    post_number: DataTypes.INTEGER,
    title: DataTypes.STRING,
    contents: DataTypes.STRING,
    date: DataTypes.STRING,
    view: DataTypes.INTEGER,
    comment_number: DataTypes.INTEGER,
    recommends: DataTypes.INTEGER,
    user_id: DataTypes.STRING,
    board_code: DataTypes.STRING
  }, {
    sequelize,
    modelName: 'board',
  });
  return board;
};