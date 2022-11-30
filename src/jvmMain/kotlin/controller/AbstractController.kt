package controller

import factory.CommandFactory

abstract class AbstractController : Controller {
    protected val factory = CommandFactory()
}